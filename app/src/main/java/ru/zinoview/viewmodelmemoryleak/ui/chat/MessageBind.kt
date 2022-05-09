package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.widget.ImageView
import android.widget.TextView
import ru.zinoview.viewmodelmemoryleak.ui.core.Bind

interface MessageBind : Bind<TextView> {

    fun bind(view: TextView, stateImage: ImageView, editImage: ImageView) = Unit
}