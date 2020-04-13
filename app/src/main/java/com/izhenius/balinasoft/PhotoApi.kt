package com.izhenius.balinasoft

import com.izhenius.balinasoft.entity.PagePhotoTypeDtoOut
import com.izhenius.balinasoft.entity.PhotoDtoOut
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.io.File

interface PhotoApi {
    @GET("api/v2/photo/type")
    fun getPhotoTypes(
        @Query("page")
        page: Int
    ): Call<PagePhotoTypeDtoOut>

    @Multipart
    @POST("/api/v2/photo")
    fun uploadPhoto(
        @Part
        name: MultipartBody.Part,
        @Part
        photo: MultipartBody.Part,
        @Part
        typeId: MultipartBody.Part
    ): Call<PhotoDtoOut>
}
