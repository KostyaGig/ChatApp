package ru.zinoview.viewmodelmemoryleak.ui.join

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri

interface Bitmap {

    fun bitmap(uri: Uri) : android.graphics.Bitmap

    fun bitmap(base64String: String) : android.graphics.Bitmap

    class Base(
        private val contentResolver: ContentResolver
    ) : Bitmap {

        override fun bitmap(uri: Uri) : android.graphics.Bitmap {
            val stream = contentResolver.openInputStream(uri)
            return BitmapFactory.decodeStream(stream)
        }

        override fun bitmap(base64String: String): android.graphics.Bitmap {
            val decodedString = android.util.Base64.decode(base64String,android.util.Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        }
    }

}