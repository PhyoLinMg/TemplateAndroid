package com.elemental.templateapplication.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import id.zelory.compressor.Compressor
import java.io.ByteArrayOutputStream
import java.io.File

object ImageUtils {
    fun ConvertImageFiletoBase64(context:Context,uri:Uri):String{
        val imageFile= File(MyImageFilePath.getPath(context, uri))
        val currentPath=imageFile.absolutePath
        val compressedImage = Compressor(context)
            .setMaxWidth(640)
            .setMaxHeight(480)
            .setQuality(75)
            .setDestinationDirectoryPath(context.externalCacheDir?.absolutePath)
            .compressToFile(File(currentPath))
            return convertToBase64(compressedImage.toString())
    }
    private fun convertToBase64(imagePath: String): String {

        val bm = BitmapFactory.decodeFile(imagePath)

        val baos = ByteArrayOutputStream()

        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val byteArrayImage = baos.toByteArray()

        return Base64.encodeToString(byteArrayImage, Base64.DEFAULT)

    }
}