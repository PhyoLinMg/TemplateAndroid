package com.elemental.templateapplication.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.elemental.templateapplication.utils.Constants.PERMISSION_REQUEST_CODE

object RequestPermissions {
    private fun checkPermission(context: Context, vararg permissions:String): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(activity:Activity, permissions: Array<String>){
        ActivityCompat.requestPermissions(
            activity, permissions ,
            PERMISSION_REQUEST_CODE
        )
    }

}