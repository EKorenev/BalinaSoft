package com.izhenius.balinasoft.data.api

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PhotoProvider {

    private var photoApi: PhotoApi? = null

    fun provideApi(retrofit: Retrofit): PhotoApi {
        if (photoApi == null) {
            photoApi = retrofit.create(PhotoApi::class.java)
        }
        return photoApi!!
    }

    fun provideRetrofit(baseUrl: String, gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    fun provideGson(): Gson {
        return Gson().newBuilder().create()
    }
}
