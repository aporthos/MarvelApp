package com.globant.shared.utils.extensions

import com.globant.shared.domain.model.APIError
import com.google.gson.Gson
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun String?.parseErrorBody(): APIError? {
    return try {
        this?.let {
            val errorObject = Gson().fromJson(it, APIError::class.java)
            errorObject
        }
    } catch (exception: Exception) {
        null
    }
}

fun String?.parseError(): String? {
    return try {
        this?.let {
            val errorObject = Gson().fromJson(it, APIError::class.java)
            errorObject.code
        }
    } catch (exception: Exception) {
        return exception.createError()
    }
}


fun String?.value(default: String = ""): String = this ?: default

fun String.md5(): String {
    if (isNullOrEmpty()) {
        return ""
    }
    val md5: MessageDigest
    return try {
        md5 = MessageDigest.getInstance("MD5")
        val bytes = md5.digest(toByteArray())
        bytes2Hex(bytes)
    } catch (e: NoSuchAlgorithmException) {
        ""
    }
}

private fun bytes2Hex(bts: ByteArray): String {
    var des = ""
    var tmp: String
    for (i in bts.indices) {
        tmp = Integer.toHexString(bts[i].toInt() and 0xFF)
        if (tmp.length == 1) {
            des += "0"
        }
        des += tmp
    }
    return des
}