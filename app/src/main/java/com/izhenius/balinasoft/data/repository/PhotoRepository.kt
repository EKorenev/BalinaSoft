package com.izhenius.balinasoft.data.repository

import com.izhenius.balinasoft.data.entity.PhotoTypeDtoOut
import com.izhenius.balinasoft.ui.screen.list.presenter.BasePresenter
import com.izhenius.balinasoft.ui.screen.list.presenter.PhotoTypePresenter
import java.io.File

interface PhotoRepository {
    fun getListPhotoTypeSize(): Int
    fun getPhotoType(index: Int): PhotoTypeDtoOut?
    fun isLastPage(): Boolean
    fun getPageSize(): Int
    fun loadPhotoTypes(presenter: BasePresenter)
    fun uploadPhoto(presenter: PhotoTypePresenter, photoTypeId: Int, photo: File)
}
