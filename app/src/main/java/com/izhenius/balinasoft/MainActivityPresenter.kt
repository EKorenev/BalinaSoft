package com.izhenius.balinasoft

import android.net.Uri
import androidx.core.content.FileProvider
import com.izhenius.balinasoft.utils.ImageCreator
import okio.IOException
import java.io.File

class MainActivityPresenter : PhotoTypePresenter {

    private var view: BaseView? = null
    private var currentPhotoTypeId: Int? = null
    private var isLoading: Boolean = false

    override fun takePhoto(photoTypeIndex: Int) {
        view?.let {
            currentPhotoTypeId = photoTypeIndex
            dispatchTakePictureIntent(it)
        }
    }

    override fun cancelPhotoUploading() {
        if (currentPhotoTypeId != null)
            currentPhotoTypeId = null
    }

    override fun setView(view: BaseView) {
        this.view = view
    }

    override fun loadData() {
        isLoading = true
        PhotoDataBase.loadPhotoTypes(this)
    }

    override fun loadNewPhoto(visibleItemCount: Int, firstVisibleItemPosition: Int, totalItemCount: Int) {
        if (!isLoading && !PhotoDataBase.isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PhotoDataBase.pageSize
            ) {
                isLoading = true
                PhotoDataBase.loadPhotoTypes(this)
            }
        }
    }

    override fun uploadPhoto() {
        currentPhotoTypeId?.let { id ->
            ImageCreator.getCurrentImageFile()?.let { imageFile ->
                PhotoDataBase.uploadPhoto(this, id, imageFile)
            }
        }
    }

    override fun onDataLoadResult(isSuccess: Boolean) {
        if (isSuccess) {
            isLoading = false
            view?.updateFields()
        } else {
            view?.showToast("Load error!")
        }
    }

    override fun onPhotoUploadResult(isSuccess: Boolean) {
        if (isSuccess) {
            view?.showToast("Photo is uploaded!")
        } else {
            view?.showToast("Upload error!")
        }
    }

    private fun dispatchTakePictureIntent(view: BaseView) {
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
