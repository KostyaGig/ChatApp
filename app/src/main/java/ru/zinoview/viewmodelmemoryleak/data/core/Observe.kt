package ru.zinoview.viewmodelmemoryleak.data.core

interface Observe<T> {

    fun observe(block:(T) -> Unit)
}