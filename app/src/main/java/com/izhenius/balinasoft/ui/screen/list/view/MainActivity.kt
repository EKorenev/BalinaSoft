package com.izhenius.balinasoft.ui.screen.list.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.izhenius.balinasoft.R
import com.izhenius.balinasoft.ui.screen.list.presenter.MainActivityPresenter
import com.izhenius.balinasoft.ui.screen.list.presenter.PhotoTypePresenter

const val REQUEST_TAKE_PHOTO = 1

class MainActivity : FragmentActivity(),
    PhotoTypeView,
    OnPhotoTypeListener {

    private lateinit var imageConnectionError: ImageView
    private lateinit var adapter: RecyclerView.Adapter<PhotoTypeDtoOutAdapter.ViewHolder>
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var presenter: PhotoTypePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageConnectionError = findViewById(R.id.imageConnectionError)
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

        presenter = MainActivityPresenter(this)
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

    override fun showConnectionError() {
        InternetErrorDialogFragment().show(supportFragmentManager, "InternetErrorDialogFragment")
        if (adapter.itemCount == 0) {
            imageConnectionError.visibility = View.VISIBLE
        }
    }

    override fun hideConnectionError() {
        if (adapter.itemCount > 0) {
            imageConnectionError.visibility = View.INVISIBLE
        }
    }

    override fun openTakePictureIntent(imageURI: Uri) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI)
                startActivityForResult(
                    takePictureIntent,
                    REQUEST_TAKE_PHOTO
                )
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
