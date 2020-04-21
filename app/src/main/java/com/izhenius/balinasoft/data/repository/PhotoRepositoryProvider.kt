package com.izhenius.balinasoft.data.repository

import com.izhenius.balinasoft.data.api.PhotoProvider
import com.izhenius.balinasoft.data.database.PhotoData
import com.izhenius.balinasoft.data.database.PhotoDatabase

private const val BASE_URL = "https://junior.balinasoft.com/"

fun providePhotoRepository(): PhotoRepository {
    return PhotoRepositoryRemote(
        PhotoProvider.provideApi(
            PhotoProvider.provideRetrofit(
                BASE_URL,
                PhotoProvider.provideGson(),
                PhotoProvider.provideOkHttp()
            )
        ),
        providePhotoDatabase()
    )
}

fun providePhotoDatabase(): PhotoDatabase {
    return PhotoData
}
