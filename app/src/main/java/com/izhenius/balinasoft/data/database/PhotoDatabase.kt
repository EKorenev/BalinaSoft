package com.izhenius.balinasoft.data.database

import com.izhenius.balinasoft.data.entity.PagePhotoTypeDtoOut
import com.izhenius.balinasoft.data.entity.PhotoTypeDtoOut

interface PhotoDatabase {
    fun getListPhotoTypeSize(): Int
    fun getPhotoType(index: Int): PhotoTypeDtoOut?
    fun isLastPage(): Boolean
    fun getNextPage(): Int
    fun updateData(pagePhotoTypeDtoOut: PagePhotoTypeDtoOut)
}
