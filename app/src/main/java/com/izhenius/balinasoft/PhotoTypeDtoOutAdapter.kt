package com.izhenius.balinasoft

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.izhenius.balinasoft.entity.PhotoTypeDtoOut
import com.izhenius.balinasoft.utils.ImageLoader

class PhotoTypeDtoOutAdapter(private val onPhotoTypeListener: OnPhotoTypeListener) :
    RecyclerView.Adapter<PhotoTypeDtoOutAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_photo_type, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            onPhotoTypeListener.onPhotoTypeClick(viewHolder.adapterPosition)
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return PhotoDataBase.getListPhotoTypeSize()
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val photoTypeDtoOut = PhotoDataBase.getPhotoType(position)
        viewHolder.bind(photoTypeDtoOut)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.imageViewPhotoType)
        private var imageViewError = itemView.findViewById<ImageView>(R.id.imageViewNoUserPhotoType)
        private var progressBar = itemView.findViewById<ProgressBar>(R.id.progressBarPhotoType)
        private val textViewHeader = itemView.findViewById<TextView>(R.id.textViewPhotoTypeHeader)
        private val textViewDetails =
            itemView.findViewById<TextView>(R.id.textViewPhotoTypeDetailsValueId)
        private val drawableNoUser = R.drawable.image_no_user

        fun bind(photoType: PhotoTypeDtoOut?) {
            photoType ?: return
            if (photoType.image.isNullOrEmpty()) {
                ImageLoader.loadResource(imageView, drawableNoUser, drawableNoUser, true)
            } else {
                ImageLoader.loadImage(photoType.image, imageView, progressBar, imageViewError, true)
            }
            textViewHeader.text = photoType.name
            textViewDetails.text = photoType.id.toString()
        }
    }
}
