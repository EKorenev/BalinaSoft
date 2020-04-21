package com.izhenius.balinasoft.ui.screen.list.presenter

import android.net.Uri
import androidx.core.content.FileProvider
import com.izhenius.balinasoft.data.database.PhotoData
import com.izhenius.balinasoft.data.repository.providePhotoRepository
import com.izhenius.balinasoft.ui.screen.list.view.PhotoTypeView
import com.izhenius.balinasoft.utils.ImageCreator
import okio.IOException
import java.io.File

class MainActivityPresenter(private val view: PhotoTypeView) :
    PhotoTypePresenter {

    private val photoRepository = providePhotoRepository()
    private var currentPhotoTypeId: Int? = null
    private var isLoading: Boolean = false

    override fun takePhoto(photoTypeIndex: Int) {
        currentPhotoTypeId = photoTypeIndex
        dispatchTakePictureIntent()
    }

    override fun cancelPhotoUploading() {
        if (currentPhotoTypeId != null)
            currentPhotoTypeId = null
    }

    override fun loadData() {
        isLoading = true
        photoRepository.loadPhotoTypes(this)
    }

    override fun loadNewPhoto(
        visibleItemCount: Int,
        firstVisibleItemPosition: Int,
        totalItemCount: Int
    ) {
        if (!isLoading && !PhotoData.isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PhotoData.pageSize
            ) {
                isLoading = true
                photoRepository.loadPhotoTypes(this)
            }
        }
    }

    override fun uploadPhoto() {
        currentPhotoTypeId?.let { id ->
            ImageCreator.getCurrentImageFile()?.let { imageFile ->
                photoRepository.uploadPhoto(
                    this,
                    id,
                    imageFile
                )
            }
        }
    }

    override fun onDataLoadResult(isSuccess: Boolean) {
        if (isSuccess) {
            isLoading = false
            view.updateFields()
        } else {
            view.showToast("Load error!")
        }
    }

    override fun onPhotoUploadResult(isSuccess: Boolean) {
        if (isSuccess) {
            view.showToast("Photo is uploaded!")
        } else {
            view.showToast("Upload error!")
        }
    }

    private fun dispatchTakePictureIntent() {
        val context = view.getContext()
        val photoFile: File? = try {
            ImageCreator.createImageFile(context)
        } catch (ex: IOException) {
            view.showToast("Can't open camera!")
            null
        }
        photoFile?.also {
            val imageURI: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                it
            )
            view.openTakePictureIntent(imageURI)
        }
    }
}
