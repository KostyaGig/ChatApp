package ru.zinoview.viewmodelmemoryleak.data.core

interface Observe<T> {

    suspend fun observe(block:(T) -> Unit)
}