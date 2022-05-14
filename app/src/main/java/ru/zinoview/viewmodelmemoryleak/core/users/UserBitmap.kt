package ru.zinoview.viewmodelmemoryleak.core.users

import android.graphics.Bitmap
import android.widget.ImageView
import ru.zinoview.viewmodelmemoryleak.ui.core.Bind

interface UserBitmap : Bind<ImageView> {

    override fun bind(view: ImageView) = Unit

    class Base(
        private val bitmap: Bitmap
    ) : UserBitmap {

        override fun bind(view: ImageView) = view.setImageBitmap(bitmap)
    }

    object Empty : UserBitmap
}