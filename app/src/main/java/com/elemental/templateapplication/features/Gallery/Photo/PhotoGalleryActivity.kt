package com.elemental.templateapplication.features.Gallery.Photo

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elemental.templateapplication.R

import com.elemental.templateapplication.databinding.ActivityPhotoGalleryBinding
import com.elemental.templateapplication.features.Gallery.BaseActivity
import com.elemental.templateapplication.features.Gallery.GalleryAdapter

import com.elemental.templateapplication.features.Gallery.data.GalleryMedia
import com.elemental.templateapplication.utils.Constants
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PhotoGalleryActivity : BaseActivity() {
    private lateinit var binding: ActivityPhotoGalleryBinding

    private lateinit var selectedImage: String
    private lateinit var imageList: ArrayList<GalleryMedia>
    private lateinit var directoryList: ArrayList<String>
    private lateinit var selectedImageList: ArrayList<String>
    private lateinit var rvGallery: RecyclerView
    private lateinit var rvSelectedImages: RecyclerView

    private var isSingleUpload = true
    private var selectedFolderIndex = 0

    private var isCropping = false
    private var isCropped = false
    private var croppedImage = ""

    private var isNavigatedToBack = true

    //TODO :: Folder Selection
    private var resImg = intArrayOf(R.drawable.ic_camera)
    private var title = arrayOf("Camera")

    private var mCurrentPhotoPath: String? = null
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var selectedGalleryAdapter: SelectedGalleryAdapter
    private var projection = arrayOf(MediaStore.MediaColumns.DATA)

    companion object {
        private val TAG = PhotoGalleryActivity::class.java.simpleName

        const val REQUEST_IMAGE_CAPTURE = 1
        const val PICK_IMAGES = 2
        const val STORAGE_PERMISSION = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhotoGalleryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        init()
    }

    private fun init() {
        //setupStatusBar()

        if (intent.hasExtra(Constants.GALLERY_TYPE_EXTRA)) {
            isSingleUpload =
                intent.getStringExtra(Constants.GALLERY_TYPE_EXTRA) == GalleryType.SINGLE.toString()
        }

        rvGallery = binding.rvGallery
        rvSelectedImages = binding.rvSelectedImages

        selectedImage = ""
        selectedImageList = ArrayList()
        imageList = ArrayList()
        directoryList = ArrayList()

        toggleActionButton(false)

        binding.btnClose.setOnClickListener {
            isNavigatedToBack = true
            onBackPressed()
        }
        binding.btnDone.setOnClickListener {
            isNavigatedToBack = false
            onBackPressed()
        }
        binding.btnCloseCropped.setOnClickListener {
            toggleCropImageView(false)
        }
        binding.btnCrop.setOnClickListener {
            toggleCropImageView(true)
            binding.cropImageView.setImageUriAsync(Uri.fromFile(File(selectedImage)))
        }
        binding.btnConfirmCropped.setOnClickListener {
            saveCroppedImage(binding.cropImageView.croppedImage)
            toggleCropImageView(false)
            setSelectedImage(croppedImage, false)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.llCropAction.elevation = Constants.ELEVATION_2F
        }

        binding.btnRatio11.setOnClickListener {
            binding.cropImageView.setAspectRatio(1, 1)
            binding.cropImageView.setFixedAspectRatio(true)

            binding.btnRatio11.setColorFilter(
                ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP
            )
            binding.btnRatio54.setColorFilter(
                ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP
            )
            binding.btnRatioFree.setColorFilter(
                ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP
            )
        }

        binding.btnRatio54.setOnClickListener {
            binding.cropImageView.setAspectRatio(5, 4)
            binding.cropImageView.setFixedAspectRatio(true)

            binding.btnRatio11.setColorFilter(
                ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP
            )
            binding.btnRatio54.setColorFilter(
                ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP
            )
            binding.btnRatioFree.setColorFilter(
                ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP
            )
        }

        binding.btnRatioFree.setOnClickListener {
            binding.cropImageView.setFixedAspectRatio(false)

            binding.btnRatio11.setColorFilter(
                ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP
            )
            binding.btnRatio54.setColorFilter(
                ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP
            )
            binding.btnRatioFree.setColorFilter(
                ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP
            )
        }

        if (isStoragePermissionGranted()) {
            getAllImages()
            setImageList()
            setFolderList()

            if (isSingleUpload) {
                binding.selectedImageFrame.visibility = View.VISIBLE
                binding.rvSelectedImages.visibility = View.GONE
            } else {
                binding.selectedImageFrame.visibility = View.GONE
                binding.rvSelectedImages.visibility = View.VISIBLE
                setSelectedImageList()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                if (mCurrentPhotoPath != null) {
                    addImage(mCurrentPhotoPath)
                }
            } else if (requestCode == PICK_IMAGES) {
                if (data != null) {
                    if (data.clipData != null) {
                        val mClipData = data.clipData
                        for (i in 0 until mClipData!!.itemCount) {
                            val item = mClipData.getItemAt(i)
                            val uri: Uri = item.uri
                            getImageFilePath(uri)
                        }
                    } else if (data.data != null) {
                        val uri: Uri = data.data!!
                        getImageFilePath(uri)
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getAllImages()
            setImageList()
            setFolderList()

            if (isSingleUpload) {
                binding.selectedImageFrame.visibility = View.VISIBLE
                binding.rvSelectedImages.visibility = View.GONE
            } else {
                binding.selectedImageFrame.visibility = View.GONE
                binding.rvSelectedImages.visibility = View.VISIBLE
                setSelectedImageList()
            }
        } else if (requestCode == STORAGE_PERMISSION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            selectedImage = ""
            selectedImageList.clear()
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        if (isCropping) {
            binding.btnCloseCropped.performClick()
        } else {
            if (isNavigatedToBack) {
                selectedImage = ""
                /**Not clear image list on normal back pressed
                 * just clear on single image request**/
                //selectedImageList.clear()
            }

            val intent = Intent()

            if (isSingleUpload) {
                if (selectedImage != "") {
                    intent.putExtra(Constants.IS_PHOTO_ADDED_EXTRA, true)
                    intent.putExtra(
                        Constants.PHOTO_EXTRA,
                        if (isCropped) croppedImage else selectedImage
                    )
                } else {
                    intent.putExtra(Constants.IS_PHOTO_ADDED_EXTRA, false)
                }

                setResult(Constants.SINGLE_PHOTO_REQUEST_CODE, intent)
            } else {
                if (selectedImageList.size > 0) {
                    intent.putExtra(Constants.IS_PHOTO_LIST_ADDED_EXTRA, true)
                    intent.putExtra(Constants.PHOTO_LIST_EXTRA, selectedImageList)
                } else {
                    intent.putExtra(Constants.IS_PHOTO_LIST_ADDED_EXTRA, false)
                }

                setResult(Constants.PHOTO_LIST_REQUEST_CODE, intent)
            }

            super.onBackPressed()
        }
    }

    // Same functionality added to BaseActivity
    /*private fun setupStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(
                this,
                R.color.colorStatusBar
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }*/

    private fun toggleActionButton(flag: Boolean) {
        if (flag) {
            binding.btnDone.isEnabled = true
            binding.btnDone.setTextColor(
                ContextCompat.getColor(this, R.color.colorAccent)
            )
        } else {
            binding.btnDone.isEnabled = false
            binding.btnDone.setTextColor(
                ContextCompat.getColor(this, R.color.disabled_text)
            )
        }

        toggleSelectedPhotos(flag)
    }

    private fun toggleSelectedPhotos(flag: Boolean) {
        if (flag) {
            binding.llEmptyPhotos.visibility = View.GONE
            if (isSingleUpload) {
                binding.selectedImageFrame.visibility = View.VISIBLE
            } else {
                binding.rvSelectedImages.visibility = View.VISIBLE
            }
        } else {
            binding.llEmptyPhotos.visibility = View.VISIBLE
            if (isSingleUpload) {
                binding.selectedImageFrame.visibility = View.GONE
            } else {
                binding.rvSelectedImages.visibility = View.GONE
            }
        }
    }

    private fun toggleCropImageView(flag: Boolean) {
        isCropping = flag
        if (flag) {
            binding.btnClose.visibility = View.GONE
            binding.llSelected.visibility = View.GONE
            binding.rvGallery.visibility = View.GONE
            binding.btnDone.visibility = View.GONE
            binding.tilFolder.visibility = View.GONE

            binding.frameImageCrop.visibility = View.VISIBLE
            binding.btnCloseCropped.visibility = View.VISIBLE
            binding.btnConfirmCropped.visibility = View.VISIBLE
        } else {
            binding.frameImageCrop.visibility = View.GONE
            binding.btnCloseCropped.visibility = View.GONE
            binding.btnConfirmCropped.visibility = View.GONE

            binding.btnClose.visibility = View.VISIBLE
            binding.llSelected.visibility = View.VISIBLE
            binding.rvGallery.visibility = View.VISIBLE
            binding.btnDone.visibility = View.VISIBLE
            binding.tilFolder.visibility = View.VISIBLE
        }
    }

    private fun setImageList() {
        rvGallery.layoutManager = GridLayoutManager(applicationContext, 3)
        galleryAdapter = GalleryAdapter(applicationContext, imageList)
        rvGallery.adapter = galleryAdapter

        galleryAdapter.setOnItemClickListener(object : GalleryAdapter.onRecyclerItemClickedListener {
            override fun onRecyclerItemClicked(position: Int, name: String, v: View?) {
                when (galleryAdapter.getItemViewType(position)) {
                    GalleryAdapter.MEDIA_PICKER -> {
                        takePicture()
                    }
                    //TODO :: Folder Selection
                    /*1 -> {
                        getPickImageIntent()
                    }*/
                    else -> {
                        try {
                            for (i in imageList.indices) {
                                if (imageList[i].name == name) {
                                    if (!imageList[i].isSelected) {
                                        selectImage(name)
                                    } else {
                                        unSelectImage(name)
                                    }
                                }
                            }
                        } catch (ed: ArrayIndexOutOfBoundsException) {
                            ed.printStackTrace()
                        }
                    }
                }
            }
        })
        setImagePickerList()
    }

    // Set selected image into profile view
    private fun setSelectedImage(image: String, isToResetCrop: Boolean = true) {
        if (!isFinishing) {
            Glide.with(this)
                .load(image)
                .placeholder(R.color.white)
                .into(binding.ivSelectedImage)
        }

        if (isToResetCrop) {
            selectedImage = image
            isCropped = false
            croppedImage = ""
        }
    }

    private fun setSelectedImageList() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvSelectedImages.layoutManager = layoutManager
        selectedGalleryAdapter = SelectedGalleryAdapter(this, selectedImageList)
        rvSelectedImages.adapter = selectedGalleryAdapter

        selectedGalleryAdapter.setOnItemClickListener(object :
            SelectedGalleryAdapter.OnItemClickListener {
            override fun onRemoveItemClick(position: Int, v: View?) {
                for (i in imageList.indices) {
                    if (selectedImageList[position] == imageList[i].name) {
                        unSelectImage(imageList[i].name)
                        break
                    }
                }
            }

        })
    }

    // Add Camera and Folder in ArrayList
    private fun setImagePickerList() {
        for (i in resImg.indices) {
            val galleryMedia = GalleryMedia("", title[i], resImg[i], false)
            imageList.add(i, galleryMedia)
        }
        galleryAdapter.notifyDataSetChanged()
    }

    // set folder list
    private fun setFolderList() {
        binding.etFolder.setText(

                directoryList[0].substringAfterLast("/", directoryList[0])

        )
        binding.etFolder.setOnClickListener {
            val directoryNames = ArrayList<String>()
            for (directory in directoryList) {
                directoryNames.add(directory.substringAfterLast("/", directory))
            }
            showStringSearchDialog(
                items = directoryNames,
                title = "Select a folder",
                hint = "Find a folder...",
                action = ::setSelectedFolder
            )
        }
        binding.tilFolder.setEndIconOnClickListener { binding.etFolder.performClick() }
    }

    private fun setSelectedFolder(position: Int) {
        selectedFolderIndex = position
        val folderName = directoryList[position]
        binding.etFolder.setText(
            folderName.substringAfterLast("/", folderName)
        )
        if (this::galleryAdapter.isInitialized) {
            if (position <= 0) {
                galleryAdapter.setData(imageList)
            } else {
                val filteredImageList =
                    if (folderName != "") imageList.filter { it.name.startsWith(folderName) }
                    else imageList
                galleryAdapter.setData(filteredImageList)
            }
        }
    }

    // get all images from external storage
    private fun getAllImages() {
        imageList.clear()
        directoryList.clear()
        val cursor: Cursor? = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )
        while (cursor!!.moveToNext()) {
            val absolutePathOfImage: String =
                cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))
            val galleryPhoto = GalleryMedia(absolutePathOfImage, "", 0, false)
            val folder = absolutePathOfImage.substringBeforeLast("/", absolutePathOfImage)
            if (!directoryList.contains(folder)) directoryList.add(folder)
            imageList.add(galleryPhoto)
        }
        cursor.close()
        imageList.reverse()
        directoryList.sortBy { it.substringAfterLast("/") }
        directoryList.add(0, "All Photos")
        //Log.e(TAG, directoryList.toString())
    }

    // start the image capture Intent
    private fun takePicture() {
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Continue only if the File was successfully created;
        val photoFile = createImageFile()
        if (photoFile != null) {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile))
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

//TODO :: Folder Selection
/*private fun getPickImageIntent() {
    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.type = "image/*"
    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
    startActivityForResult(intent, PICK_IMAGES)
}*/*/

    // Add image in SelectedArrayList
    private fun selectImage(name: String) {
        for (index in imageList.indices) {
            if (imageList[index].name == name) {
                if (isSingleUpload) {
                    for (i in imageList.indices) {
                        if (imageList[i].name == selectedImage) {
                            imageList[i].isSelected = false
                            galleryAdapter.notifyItemChanged(i)
                            break
                        }
                    }
                    imageList[index].isSelected = true
                    galleryAdapter.notifyItemChanged(index)

                    setSelectedImage(imageList[index].name)
                } else {
                    // Check before add new item in ArrayList;
                    if (!selectedImageList.contains(imageList[index].name)) {
                        imageList[index].isSelected = true
                        /*selectedImageList.add(0, imageList[position].name)
                        selectedGalleryAdapter.notifyItemInserted(0)*/
                        selectedImageList.add(imageList[index].name)
                        selectedGalleryAdapter.notifyItemInserted(selectedImageList.size - 1)
                        galleryAdapter.notifyItemChanged(index)

                        //rvSelectedImages.smoothScrollToPosition(0)
                        rvSelectedImages.smoothScrollToPosition(selectedImageList.size - 1)
                    }
                }
                setSelectedFolder(selectedFolderIndex)
                break
            }
        }

        toggleActionButton(true)
    }

    // Remove image from selectedImageList
    fun unSelectImage(name: String) {
        for (index in imageList.indices) {
            if (imageList[index].name == name) {
                if (isSingleUpload) {
                    imageList[index].isSelected = false
                    galleryAdapter.notifyItemChanged(index)

                    setSelectedImage("")

                    toggleActionButton(false)
                } else {
                    for (i in selectedImageList.indices) {
                        if (imageList[index].name != "") {
                            if (selectedImageList[i] == imageList[index].name) {
                                imageList[index].isSelected = false
                                selectedImageList.removeAt(i)
                                selectedGalleryAdapter.notifyItemRemoved(i)
                                galleryAdapter.notifyItemChanged(index)
                                break
                            }
                        }
                    }

                    if (selectedImageList.isEmpty()) {
                        toggleActionButton(false)
                    }
                }
                setSelectedFolder(selectedFolderIndex)
                break
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }

    // Get image file path
    private fun getImageFilePath(uri: Uri) {
        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val absolutePathOfImage: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))
                if (absolutePathOfImage != null) {
                    checkImage(absolutePathOfImage)
                } else {
                    checkImage(uri.toString())
                }
            }
        }
    }

    private fun checkImage(filePath: String?) {
        // Check before adding a new image to ArrayList to avoid duplicate images
        if (!selectedImageList.contains(filePath)) {
            for (pos in imageList.indices) {
                if (imageList[pos].name != null) {
                    if (imageList[pos].name.equals(filePath, ignoreCase = true)) {
                        imageList.removeAt(pos)
                    }
                }
            }
            addImage(filePath)
        }
    }

    // add image in selectedImageList and imageList
    private fun addImage(filePath: String?) {
        val galleryPhoto = GalleryMedia(filePath!!, "", 0, true)
        imageList.add(resImg.size, galleryPhoto)

        if (isSingleUpload) {
            unSelectImage(selectedImage)
            setSelectedImage(filePath)
        } else {
            selectedImageList.add(0, filePath)
            selectedGalleryAdapter.notifyDataSetChanged()
            galleryAdapter.notifyDataSetChanged()
        }
        setSelectedFolder(0)

        toggleActionButton(true)
    }

    private fun saveCroppedImage(bitmap: Bitmap) {
        try {
            val myDir = applicationContext.getDir(Constants.APP_TEMPS_DIR, Context.MODE_PRIVATE)

            val tempImage = File(myDir, "Cropped_${System.currentTimeMillis()}.jpg")

            // Output stream to write file
            val output = FileOutputStream(tempImage)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, output)

            // flushing output
            output.flush()

            // closing streams
            output.close()

            isCropped = true
            croppedImage = tempImage.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun isStoragePermissionGranted(): Boolean {
        val ACCESS_EXTERNAL_STORAGE =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (ACCESS_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION
            )
            return false
        }
        return true
    }
}
