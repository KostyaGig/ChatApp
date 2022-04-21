package ru.zinoview.viewmodelmemoryleak.ui.chat

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface ObserveScrollCommunication {

    fun observeScrollCommunication(owner: LifecycleOwner, observer: Observer<UiScroll>)
}