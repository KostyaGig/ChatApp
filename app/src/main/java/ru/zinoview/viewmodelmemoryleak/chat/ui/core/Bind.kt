package ru.zinoview.viewmodelmemoryleak.chat.ui.core

import android.widget.ImageView
import android.widget.TextView

interface Bind {

    fun bind(view: TextView)
    fun bind(view: TextView,stateImage: ImageView,editImage: ImageView)
}