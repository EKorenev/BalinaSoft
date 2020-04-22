package com.izhenius.balinasoft.data.repository

import com.izhenius.balinasoft.data.api.PhotoApi
import com.izhenius.balinasoft.data.database.PhotoDatabase
import com.izhenius.balinasoft.data.entity.PagePhotoTypeDtoOut
import com.izhenius.balinasoft.data.entity.PhotoDtoOut
import com.izhenius.balinasoft.data.entity.PhotoTypeDtoOut
import com.izhenius.balinasoft.ui.screen.list.presenter.BasePresenter
import com.izhenius.balinasoft.ui.screen.list.presenter.PhotoTypePresenter
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PhotoRepositoryRemote(private val api: PhotoApi, private val database: PhotoDatabase) :
    PhotoRepository {
    override fun getListPhotoTypeSize(): Int {
        return database.getListPhotoTypeSize()
    }

    override fun getPhotoType(index: Int): PhotoTypeDtoOut? {
        return database.getPhotoType(index)
    }

    override fun isLastPage(): Boolean {
        return database.isLastPage()
    }

    override fun getPageSize(): Int {
        return database.getPageSize()
    }

    override fun loadPhotoTypes(presenter: BasePresenter) {
        api.getPhotoTypes(database.getNextPage()).enqueue(object : Callback<PagePhotoTypeDtoOut> {
            override fun onFailure(call: Call<PagePhotoTypeDtoOut>, t: Throwable) {
                presenter.onDataLoadResult(false)
            }

            override fun onResponse(
                call: Call<PagePhotoTypeDtoOut>,
                response: Response<PagePhotoTypeDtoOut>
            ) {
                val pagePhotoTypeDtoOut = response.body()
                if (pagePhotoTypeDtoOut != null) {
                    database.updateData(
                        pagePhotoTypeDtoOut
                    )
                    presenter.onDataLoadResult(true)
                } else {
                    presenter.onDataLoadResult(false)
                }
            }
        })
    }

    override fun uploadPhoto(presenter: PhotoTypePresenter, photoTypeId: Int, photo: File) {
        val photoTypeDtoOut =
            database.getPhotoType(
                photoTypeId
            )
        if (photoTypeDtoOut == null) {
            presenter.onPhotoUploadResult(false)
        } else {
            val partBodyPhoto = MultipartBody.Part.createFormData(
                "photo",
                photo.name,
                photo.asRequestBody("image/*".toMediaType())
            )
            val requestBodyName =
                photoTypeDtoOut.name.toRequestBody("multipart/form-data".toMediaType())
            val requestBodyId = photoTypeDtoOut.id.toString()
                .toRequestBody("multipart/form-data".toMediaType())
            api.uploadPhoto(requestBodyName, partBodyPhoto, requestBodyId).enqueue(
                object : Callback<PhotoDtoOut> {
                    override fun onFailure(call: Call<PhotoDtoOut>, t: Throwable) {
                        presenter.onPhotoUploadResult(false)
                    }

                    override fun onResponse(
                        call: Call<PhotoDtoOut>,
                        response: Response<PhotoDtoOut>
                    ) {
                        presenter.onPhotoUploadResult(response.body() is PhotoDtoOut)
                    }
                }
            )
        }
    }
}
