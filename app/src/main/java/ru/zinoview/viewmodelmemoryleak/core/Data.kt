package ru.zinoview.viewmodelmemoryleak.core

interface Data<T> {

    suspend fun data(block: (T) -> Unit)
}