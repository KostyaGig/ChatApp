package ru.zinoview.viewmodelmemoryleak.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.zinoview.viewmodelmemoryleak.ui.connection.UiConnection

interface ObserveConnection {

    fun observeConnection(owner: LifecycleOwner, observer: Observer<UiConnection>)
}