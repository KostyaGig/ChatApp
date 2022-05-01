package ru.zinoview.viewmodelmemoryleak.ui.chat

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface ObserveScroll {

    fun observeScrollCommunication(owner: LifecycleOwner, observer: Observer<UiScroll>)
}