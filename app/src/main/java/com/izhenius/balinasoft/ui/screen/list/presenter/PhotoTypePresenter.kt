package com.izhenius.balinasoft.ui.screen.list.presenter

interface PhotoTypePresenter :
    BasePresenter {
    fun loadNewPhoto(visibleItemCount: Int, firstVisibleItemPosition: Int, totalItemCount: Int)
    fun uploadPhoto()
    fun onPhotoUploadResult(isSuccess: Boolean)
    fun takePhoto(photoTypeIndex: Int)
    fun cancelPhotoUploading()
}
