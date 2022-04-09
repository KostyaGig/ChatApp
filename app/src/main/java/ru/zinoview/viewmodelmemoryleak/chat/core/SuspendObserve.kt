package ru.zinoview.viewmodelmemoryleak.chat.core

interface SuspendObserve<T> {

    suspend fun observe(block:(T) -> Unit)
}