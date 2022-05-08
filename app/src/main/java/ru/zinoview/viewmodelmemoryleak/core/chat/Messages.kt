package ru.zinoview.viewmodelmemoryleak.core.chat

interface Messages<T> {

    suspend fun messages(block: (List<T>) -> Unit)
}