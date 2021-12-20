package com.example.finle_project.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.File

object ImageEncoding {

    fun decodeBase64(input: String): Bitmap? {
        val decodedString = Base64.decode(input, Base64.NO_WRAP)
        val decodedByte = ByteArrayInputStream(decodedString)
        return BitmapFactory.decodeStream(decodedByte)
    }

    fun encodeBase64(uri: Uri): String {
        val file = getFileByUri(uri)
        val encodedImage = Base64.encodeToString(file.readBytes(), Base64.NO_WRAP)

        return encodedImage
    }

    private fun getFileByUri(uri: Uri?): File {
        uri?.let {
            val filePath = it.path
            return File(filePath!!)
        }

        return File("")
    }

}