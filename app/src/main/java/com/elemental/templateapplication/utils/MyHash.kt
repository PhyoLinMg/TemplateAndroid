package com.elemental.templateapplication.utils

import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SignatureException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object MyHash {
    private const val HASH_ALGORITHM = "HmacSHA256"

    @Throws(SignatureException::class)
    fun hash(text: String,key:String): String {
        try {
            val sk = SecretKeySpec(
                key.toByteArray(charset("UTF-8")),
                HASH_ALGORITHM
            )
            val mac = Mac.getInstance(sk.algorithm)
            mac.init(sk)
            val hmac = mac.doFinal(text.toByteArray(charset("UTF-8")))
            return hmac.toHexString()

        } catch (e1: NoSuchAlgorithmException) {
            // throw an exception or pick a different encryption method
            throw SignatureException(
                "error building signature, no such algorithm in device $HASH_ALGORITHM"
            )
        } catch (e: InvalidKeyException) {
            throw SignatureException(
                "error building signature, invalid key $HASH_ALGORITHM"
            )
        }
    }
    private fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }
}