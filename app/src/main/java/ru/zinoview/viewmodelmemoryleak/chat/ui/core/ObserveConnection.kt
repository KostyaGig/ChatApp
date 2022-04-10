package ru.zinoview.viewmodelmemoryleak.chat.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.zinoview.viewmodelmemoryleak.chat.ui.connection.UiConnection

interface ObserveConnection {

    fun observeConnection(owner: LifecycleOwner, observer: Observer<UiConnection>)
}