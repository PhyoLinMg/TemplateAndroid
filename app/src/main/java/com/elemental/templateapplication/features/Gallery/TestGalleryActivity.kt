package com.elemental.templateapplication.features.Gallery


import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.elemental.templateapplication.R
import com.elemental.templateapplication.features.Gallery.Photo.GalleryType
import com.elemental.templateapplication.features.Gallery.Photo.PhotoGalleryActivity

import com.elemental.templateapplication.utils.Constants
import kotlinx.android.synthetic.main.activity_test_gallery.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class TestGalleryActivity : AppCompatActivity() ,EasyPermissions.PermissionCallbacks{

    companion object {
        const val SINGLE_PHOTO_REQUEST_CODE = 12
        const val MULTIPLE_PHOTO_REQUEST_CODE = 13
        const val AUDIO_LIST_REQUEST_CODE = 14
        const val VIDEO_REQUEST_CODE = 16
        const val SINGLE_AUDIO_REQUEST_CODE = 19

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_gallery)

        requestPermissions()
        photo_single_gallery.setOnClickListener {
            val intent = Intent(this, PhotoGalleryActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra(Constants.GALLERY_TYPE_EXTRA, GalleryType.SINGLE.toString())
            startActivityForResult(intent, SINGLE_PHOTO_REQUEST_CODE)
            overridePendingTransition(0, 0)
        }
        photo_multiple_gallery.setOnClickListener {
            val intent = Intent(this, PhotoGalleryActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra(Constants.GALLERY_TYPE_EXTRA, GalleryType.MULTIPLE.toString())
            startActivityForResult(intent, MULTIPLE_PHOTO_REQUEST_CODE)
            overridePendingTransition(0, 0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            AppSettingsDialog.Builder(this).build().show()
        }
        else
        {
            requestPermissions()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("requestCode",requestCode.toString())
        if(requestCode== SINGLE_PHOTO_REQUEST_CODE){
            test_text.text = data?.getStringExtra(Constants.PHOTO_EXTRA)
        }
        else if(requestCode== MULTIPLE_PHOTO_REQUEST_CODE){
            test_text.text=data?.getStringArrayListExtra(Constants.PHOTO_LIST_EXTRA).toString()
        }


    }
    private fun hasPermissionStorage() = EasyPermissions.hasPermissions(
        this,
        Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private fun requestPermissions(){
        if(hasPermissionStorage()) return
        EasyPermissions.requestPermissions(this,"You need to accept Permissions to use that feature",
            101,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
    }
}