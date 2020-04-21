package com.izhenius.balinasoft.data.entity

data class PagePhotoTypeDtoOut(
    val content: List<PhotoTypeDtoOut>,
    val page: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int
)
