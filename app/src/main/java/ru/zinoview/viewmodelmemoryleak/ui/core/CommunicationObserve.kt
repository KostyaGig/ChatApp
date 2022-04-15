package ru.zinoview.viewmodelmemoryleak.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface CommunicationObserve<T> {

    fun observe(owner: LifecycleOwner,observer: Observer<T>)
}