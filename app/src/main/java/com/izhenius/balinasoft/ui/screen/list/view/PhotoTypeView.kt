package com.izhenius.balinasoft.ui.screen.list.view

import android.net.Uri

interface PhotoTypeView : BaseView {
    fun openTakePictureIntent(imageURI: Uri)
}
