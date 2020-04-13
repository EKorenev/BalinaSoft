package com.izhenius.balinasoft.entity

import com.izhenius.balinasoft.entity.PhotoTypeDtoOut

data class PagePhotoTypeDtoOut(
    val content: List<PhotoTypeDtoOut>,
    val page: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int
)
