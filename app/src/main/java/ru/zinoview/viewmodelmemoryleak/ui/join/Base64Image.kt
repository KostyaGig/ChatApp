package ru.zinoview.viewmodelmemoryleak.ui.join

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import java.io.ByteArrayOutputStream
import android.util.Base64
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import android.R.drawable
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap


interface Base64Image : ru.zinoview.viewmodelmemoryleak.core.join.Base64Image<Uri> {

    fun base64Image(drawableId: Int): String

    class Base(
        private val resourceProvider: ResourceProvider,
        private val contentResolver: ContentResolver,
        private val byteArrayOutputStream: ByteArrayOutputStream
    ) : Base64Image {

        override fun base64Image(imageUri: Uri) : String {
            val input = contentResolver.openInputStream(imageUri)
            val image = BitmapFactory.decodeStream(input, null, null)
            image!!.compress(Bitmap.CompressFormat.JPEG, QUALITY, byteArrayOutputStream)
            val imageBytes = byteArrayOutputStream.toByteArray()

            return Base64.encodeToString(imageBytes, Base64.DEFAULT)
        }

        override fun base64Image(drawableId: Int): String {
            val drawable  = resourceProvider.drawable(drawableId)
            val bitmap = drawable.toBitmap()
            bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, byteArrayOutputStream)
            val imageBytes = byteArrayOutputStream.toByteArray()

            return Base64.encodeToString(imageBytes, Base64.DEFAULT)
        }


        private companion object {
            private const val QUALITY = 100
        }
    }
}