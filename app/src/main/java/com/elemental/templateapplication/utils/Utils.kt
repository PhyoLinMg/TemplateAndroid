package com.elemental.templateapplication.utils

import android.app.Activity
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object Utils {
    fun printHashKey(activity:Activity) {
        try {
            val info: PackageInfo =
                activity.packageManager.getPackageInfo(activity.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.e("hashKey", "printHashKey: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("haskKey", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("haskKey", "printHashKey()", e)
        }
    }

    fun printHashKeySafe(activity: Activity) {
        val signatureList: List<String>
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // New signature
                val sig = activity.packageManager.getPackageInfo(activity.packageName, PackageManager.GET_SIGNING_CERTIFICATES).signingInfo
                signatureList = if (sig.hasMultipleSigners()) {
                    // Send all with apkContentsSigners
                    sig.apkContentsSigners.map {
                        val digest = MessageDigest.getInstance("SHA")
                        digest.update(it.toByteArray())
                        bytesToHex(digest.digest())
                    }
                } else {
                    // Send one with signingCertificateHistory
                    sig.signingCertificateHistory.map {
                        val digest = MessageDigest.getInstance("SHA")
                        digest.update(it.toByteArray())
                        bytesToHex(digest.digest())
                    }
                }
            } else {
                val sig = activity.packageManager.getPackageInfo(activity.packageName, PackageManager.GET_SIGNATURES).signatures
                signatureList = sig.map {
                    val digest = MessageDigest.getInstance("SHA")
                    digest.update(it.toByteArray())
                    bytesToHex(digest.digest())
                }
            }


        } catch (e: NoSuchAlgorithmException) {
            Log.e("hashKey", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("hashKey", "printHashKey()", e)
        }

    }
    private fun bytesToHex(bytes: ByteArray): String {
        val hashKey = String(Base64.encode(bytes, 0))
        Log.d("hashKey",hashKey);
        return hashKey
    }
}