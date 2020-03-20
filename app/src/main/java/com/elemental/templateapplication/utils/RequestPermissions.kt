package com.elemental.templateapplication.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.elemental.templateapplication.utils.Constants.PERMISSION_REQUEST_CODE
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable



object RequestPermissions {
    var disposable: Disposable? = null


    private fun checkPermission(activity: FragmentActivity,permission:String) {
        val rxPermissions = RxPermissions(activity)
        disposable = rxPermissions
            .requestEach(permission)
            .subscribe({
                when {
                    it.granted -> {
                        Toast.makeText(activity, "Permission is Granted", Toast.LENGTH_SHORT).show()
                    }
                    it.shouldShowRequestPermissionRationale -> {
                        Toast.makeText(activity, "Permission is required", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(activity, "Permission is required", Toast.LENGTH_SHORT).show()
                    }
                }
            }, {
                Toast.makeText(activity, "Permission is required", Toast.LENGTH_SHORT).show()
            }, {
            })
    }

    private fun checkPermission(context: Context, vararg permissions: String): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    private fun requestPermission(activity: Activity, permissions: Array<String>) {
        ActivityCompat.requestPermissions(
            activity, permissions,
            PERMISSION_REQUEST_CODE
        )
    }
}