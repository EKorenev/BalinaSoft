package com.izhenius.balinasoft.utils

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object ImageCreator {

    private var currentImagePath: String? = null

    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        currentImagePath = null
        val timeStamp: String = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentImagePath = absolutePath
        }
    }

    fun getCurrentImageFile(): File? {
        return currentImagePath?.let {
            val imageFile = File(it)
            if (imageFile.exists()) {
                imageFile
            } else null
        }
    }
}
