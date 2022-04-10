package ru.zinoview.viewmodelmemoryleak.chat.ui.core

import android.widget.ImageView
import android.widget.TextView

interface Bind {

    fun bind(view: TextView)
    fun bindError(view: TextView)
    fun bind(view: TextView,imageView: ImageView)
}