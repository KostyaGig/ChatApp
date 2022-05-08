package ru.zinoview.viewmodelmemoryleak.core

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

interface ResourceProvider {

    fun string(@StringRes id: Int) : String

    fun drawable(@DrawableRes id: Int) : Drawable

    class Base(
        private val context: Context
    ) : ResourceProvider {

        override fun string(id: Int): String
            = context.getString(id)

        override fun drawable(id: Int)
            = ContextCompat.getDrawable(context,id)!!
    }
}
