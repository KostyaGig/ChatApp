package ru.zinoview.viewmodelmemoryleak.ui.chat.view

import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.ui.core.Show
import ru.zinoview.viewmodelmemoryleak.ui.core.ShowMore
import ru.zinoview.viewmodelmemoryleak.ui.join.Bitmap

interface ViewWrapper : Disconnect<Unit>, Show<Unit>, ShowMore<Unit,String> {

    override fun show(arg: Unit) = Unit
    override fun show(arg: Unit, arg2: String) = Unit

    // todo move
    fun showImage(bitmap: android.graphics.Bitmap) = Unit
    fun showImage(@IdRes id: Int) = Unit

    override fun disconnect(arg: Unit) = Unit

    class Base(
        private val view: View
    ) : ViewWrapper {

        override fun disconnect(arg: Unit) {
            view.visibility = View.GONE
        }

        override fun show(arg: Unit) {
            view.visibility = View.VISIBLE
        }
    }

    class Text(
        private val textView: TextView
    ) : ViewWrapper {

        override fun show(arg: Unit, text: String) {
            textView.text = text
        }

    }

    class EditText(
        private val editText: android.widget.EditText
    ) : ViewWrapper {

        override fun disconnect(arg: Unit) {
            editText.setText("")
        }

        override fun show(arg: Unit, arg2: String) {
            editText.setText(arg2)
        }
    }

    class Image(
        private val imageView: ImageView,
        private val resourceProvider: ResourceProvider
    ) : ViewWrapper {

        override fun showImage(bitmap: android.graphics.Bitmap) = imageView.setImageBitmap(bitmap)
        override fun showImage(id: Int) = imageView.setImageDrawable(resourceProvider.drawable(id))
    }

    object Empty : ViewWrapper
}