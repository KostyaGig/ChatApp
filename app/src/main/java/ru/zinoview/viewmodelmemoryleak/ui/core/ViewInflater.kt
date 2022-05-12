package ru.zinoview.viewmodelmemoryleak.ui.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

interface ViewInflater {

    fun inflate(parent: ViewGroup,@LayoutRes id: Int) : View

    class Base : ViewInflater {

        override fun inflate(parent: ViewGroup, id: Int): View
            = LayoutInflater.from(parent.context).inflate(id,parent,false)
    }
}