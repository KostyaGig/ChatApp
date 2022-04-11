package ru.zinoview.viewmodelmemoryleak.chat.ui.core

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import ru.zinoview.viewmodelmemoryleak.chat.core.Clean

abstract class BaseViewModel<T>(
    private val repository: Clean,
    private val communication: Communication<T>
) : ViewModel(), Clean, CommunicationObserve<T> {

    override fun clean() = repository.clean()

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) =
        communication.observe(owner, observer)

}