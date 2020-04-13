package com.izhenius.balinasoft

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PhotoProvider {

    private const val baseUrl = "https://junior.balinasoft.com/"
    private var photoApi: PhotoApi? = null

    fun provideApi(): PhotoApi {
        if (photoApi == null) {
            photoApi = getRetrofit().create(PhotoApi::class.java)
        }
        return photoApi!!
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttp())
            .build()
    }

    private fun getOkHttp(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }
}
