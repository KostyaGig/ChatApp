package ru.zinoview.viewmodelmemoryleak.data.core

interface SuspendObserve<T> {

    suspend fun observe(block:(T) -> Unit)
}