###	Feature: Pick Picture,Audio and Video

This Feature will include Audio Selecting,Photos Selecting(Single And Multiple), and Video Selecting

##### Video Selecting(Used Libraries)

- ExoPlayer

- RecyclerView

- [layout folder]()

  ```kotlin
  
  ```

  

##### Photo Selecting(Uses Libraries)

- CroppedImageView

- Glide

- RecyclerView

- [layout folder]()

  First You have to copy the photo gallery folder and add to your Project.

  ##### Single

  ```kotlin
  val intent = Intent(this, PhotoGalleryActivity::class.java)
  intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
  intent.putExtra(Constants.GALLERY_TYPE_EXTRA, GalleryType.SINGLE.toString())
  startActivityForResult(intent, SINGLE_PHOTO_REQUEST_CODE)
  overridePendingTransition(0, 0)
  ```

  ##### Multiple

  ```kotlin
  val intent = Intent(this, PhotoGalleryActivity::class.java)
  intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
  intent.putExtra(Constants.GALLERY_TYPE_EXTRA, GalleryType.MULTIPLE.toString())
  startActivityForResult(intent, MULTIPLE_PHOTO_REQUEST_CODE)
  overridePendingTransition(0, 0)
  ```

  You can call the result of the path of the image if single and the list of the path of images in OnActivityResult

  ```kotlin
  if(requestCode==SINGLE_PHOTO_REQUEST_CODE){
  	Log.d("path_image",data?.getStringExtra(Constants.PHOTO_EXTRA))	
  }
  else if(requestCode==MULTIPLE_PHOTO_REQUEST_CODE){
  	Log.d("path_image",data?.getStringArrayListExtra(Constants.PHOTO_LIST_EXTRA).toString())
  }
  ```

  

##### Audio Selecting

- Exoplayer
- RecyclerView
- [layout folder]()



