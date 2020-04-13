package com.izhenius.balinasoft.utils

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import kotlin.math.min

object ImageLoader {
    fun loadImage(
        imagePath: String,
        imageView: ImageView,
        imageViewDownloader: ProgressBar,
        imageViewError: ImageView,
        isNeedCircleTransformation: Boolean
    ) {
        if (imagePath.isEmpty())
            return

        imageViewDownloader.visibility = View.VISIBLE
        val picassoInstance = Picasso.get()
            .load(imagePath)
            .fit()
            .centerInside()
            .error(imageViewError.id)
        if (isNeedCircleTransformation) {
            picassoInstance.transform(CircleTransformation())
        }
        picassoInstance.into(imageView, object : Callback {
            override fun onSuccess() {
                imageViewDownloader.visibility = View.GONE
                imageViewError.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
                imageViewDownloader.visibility = View.GONE
                loadResource(imageView, imageViewError.id, imageViewError.id, isNeedCircleTransformation)
            }
        }
        )
    }

    class CircleTransformation : Transformation {
        override fun key(): String {
            return "circleTransformation"
        }

        override fun transform(source: Bitmap?): Bitmap? {
            source ?: return source

            val size = min(source.width, source.height)

            val x = (source.width - size) / 2
            val y = (source.height - size) / 2

            val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
            if (squaredBitmap != source) {
                source.recycle()
            }

            val bitmap = Bitmap.createBitmap(size, size, source.config)

            val canvas = Canvas(bitmap)
            val paint = Paint()
            val shader = BitmapShader(
                squaredBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
            )
            paint.shader = shader
            paint.isAntiAlias = true

            val radius: Float = size / 2f
            canvas.drawCircle(radius, radius, radius, paint)

            squaredBitmap.recycle()
            return bitmap
        }
    }

    fun loadResource(
        imageView: ImageView,
        drawable: Int,
        drawableError: Int,
        isNeedCircleTransformation: Boolean
    ) {
        val picassoInstance = Picasso.get()
            .load(drawable)
            .fit()
            .centerInside()
            .error(drawableError)
        if (isNeedCircleTransformation) {
            picassoInstance.transform(CircleTransformation())
        }
        picassoInstance
            .into(imageView)
    }
}
