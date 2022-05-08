package ru.zinoview.viewmodelmemoryleak.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.ui.join.ImageProfile

abstract class BaseViewModel<T>(
    private var communication: Communication<T>,
    private var cleans: List<Clean> = emptyList()
    ) : ViewModel(), Clean, CommunicationObserve<T> {

    override fun clean() {
        cleans.forEach { clean ->
            clean.clean()
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) =
        communication.observe(owner, observer)

}