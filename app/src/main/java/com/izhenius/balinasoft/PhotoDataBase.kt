package com.izhenius.balinasoft

import com.izhenius.balinasoft.entity.PagePhotoTypeDtoOut
import com.izhenius.balinasoft.entity.PhotoTypeDtoOut
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

object PhotoDataBase {
    private val listPhotoTypeDtoOut = ArrayList<PhotoTypeDtoOut>()
    private var currentPage: Int = -1
    private var totalPages: Int = 0
    var pageSize: Int = 0
        private set
    private var itemOnPageCount: Int = 0

    fun getListPhotoTypeSize(): Int {
        return listPhotoTypeDtoOut.size
    }

    fun getPhotoType(index: Int): PhotoTypeDtoOut? {
        return if (index >= 0 && index < listPhotoTypeDtoOut.size)
            listPhotoTypeDtoOut[index]
        else
            null
    }

    fun isLastPage(): Boolean {
        return currentPage >= totalPages
    }

    fun loadPhotoTypes(presenter: BasePresenter) {
        PhotoProvider.provideApi().getPhotoTypes(++currentPage)
            .enqueue(object : Callback<PagePhotoTypeDtoOut> {
                override fun onFailure(call: Call<PagePhotoTypeDtoOut>, t: Throwable) {
                    presenter.onDataLoadResult(false)
                }

                override fun onResponse(
                    call: Call<PagePhotoTypeDtoOut>,
                    response: Response<PagePhotoTypeDtoOut>
                ) {
                    val pagePhotoTypeDtoOut = response.body()
                    if (pagePhotoTypeDtoOut != null) {
                        updateDataBase(pagePhotoTypeDtoOut)
                        presenter.onDataLoadResult(true)
                    } else {
                        presenter.onDataLoadResult(false)
                    }
                }
            })
    }

    fun uploadPhoto(presenter: PhotoTypePresenter, photoTypeId: Int, photo: File) {
        val photoTypeDtoOut = getPhotoType(photoTypeId)
        if (photoTypeDtoOut == null) {
            presenter.onPhotoUploadResult(false)
        } else {
            presenter.onPhotoUploadResult(false)
//            PhotoProvider.provideApi().uploadPhoto(photoTypeDtoOut.name, photo, photoTypeDtoOut.id)
//                .enqueue(
//                    object : Callback<PhotoDtoOut> {
//                        override fun onFailure(call: Call<PhotoDtoOut>, t: Throwable) {
//                            presenter.onPhotoUploadResult(false)
//                        }
//
//                        override fun onResponse(
//                            call: Call<PhotoDtoOut>,
//                            response: Response<PhotoDtoOut>
//                        ) {
//                            presenter.onPhotoUploadResult(response.body() is PhotoDtoOut)
//                        }
//
//                    }
//                )
        }
    }

    private fun updateDataBase(pagePhotoTypeDtoOut: PagePhotoTypeDtoOut) {
        listPhotoTypeDtoOut.addAll(pagePhotoTypeDtoOut.content)
        currentPage = pagePhotoTypeDtoOut.page
        totalPages = pagePhotoTypeDtoOut.totalPages
        pageSize = pagePhotoTypeDtoOut.pageSize
        itemOnPageCount = pagePhotoTypeDtoOut.totalElements
    }
}
