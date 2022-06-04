package ru.zinoview.viewmodelmemoryleak.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

interface Communication<T> : CommunicationObserve<T> {

    fun postValue(value: T) = Unit
    override fun observe(owner: LifecycleOwner, observer: Observer<T>) = Unit

    object Empty : Communication<Unit>

    abstract class Base<T> : Communication<T> {
        private val liveData = MutableLiveData<T>()

        override fun observe(owner: LifecycleOwner, observer: Observer<T>)
            = liveData.observe(owner, observer)

        override fun postValue(value: T) {
            liveData.value = value
        }
    }

}