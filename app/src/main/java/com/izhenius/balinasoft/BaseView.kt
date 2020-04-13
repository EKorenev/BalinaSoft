package com.izhenius.balinasoft

import android.content.Context
import android.net.Uri

interface BaseView {
    fun getContext(): Context
    fun updateFields()
    fun showToast(text: String)
    fun openTakePictureIntent(imageURI: Uri)
}
