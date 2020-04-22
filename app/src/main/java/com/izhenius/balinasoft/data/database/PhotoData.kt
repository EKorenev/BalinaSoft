package com.izhenius.balinasoft.data.database

import com.izhenius.balinasoft.data.entity.PagePhotoTypeDtoOut
import com.izhenius.balinasoft.data.entity.PhotoTypeDtoOut

object PhotoData : PhotoDatabase {
    private val listPhotoTypeDtoOut = ArrayList<PhotoTypeDtoOut>()
    private var currentPage: Int = -1
    private var totalPages: Int = 0
    private var pageSize: Int = 0
    private var itemOnPageCount: Int = 0

    override fun getListPhotoTypeSize(): Int {
        return listPhotoTypeDtoOut.size
    }

    override fun getPhotoType(index: Int): PhotoTypeDtoOut? {
        return if (index >= 0 && index < listPhotoTypeDtoOut.size)
            listPhotoTypeDtoOut[index]
        else
            null
    }

    override fun isLastPage(): Boolean {
        return currentPage >= totalPages
    }

    override fun getPageSize(): Int {
        return pageSize
    }

    override fun getNextPage(): Int {
        return currentPage + 1
    }

    override fun updateData(pagePhotoTypeDtoOut: PagePhotoTypeDtoOut) {
        listPhotoTypeDtoOut.addAll(pagePhotoTypeDtoOut.content)
        currentPage = pagePhotoTypeDtoOut.page
        totalPages = pagePhotoTypeDtoOut.totalPages
        pageSize = pagePhotoTypeDtoOut.pageSize
        itemOnPageCount = pagePhotoTypeDtoOut.totalElements
    }
}
