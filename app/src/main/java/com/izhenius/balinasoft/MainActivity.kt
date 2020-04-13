package com.izhenius.balinasoft

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val REQUEST_TAKE_PHOTO = 1

class MainActivity : Activity(), BaseView, OnPhotoTypeListener {

    private lateinit var adapter: RecyclerView.Adapter<PhotoTypeDtoOutAdapter.ViewHolder>
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var presenter: PhotoTypePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = PhotoTypeDtoOutAdapter(this)
        layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                    presenter.loadNewPhoto(
                        visibleItemCount,
                        firstVisibleItemPosition,
                        totalItemCount
                    )
                }
            }

        recyclerView.addOnScrollListener(recyclerViewOnScrollListener)

        presenter = MainActivityPresenter()
        presenter.setView(this)
        presenter.loadData()
    }

    override fun onPhotoTypeClick(photoTypeIndex: Int) {
        presenter.takePhoto(photoTypeIndex)
    }

    override fun getContext(): Context {
        return this
    }

    override fun updateFields() {
        adapter.notifyDataSetChanged()
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun openTakePictureIntent(imageURI: Uri) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                presenter.uploadPhoto()
            } else {
                presenter.cancelPhotoUploading()
            }
        }
    }
}
