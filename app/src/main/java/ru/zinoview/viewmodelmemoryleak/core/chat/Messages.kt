package ru.zinoview.viewmodelmemoryleak.core.chat

interface Messages<T> {

    suspend fun messages(receiverId: String,block: (List<T>) -> Unit)
}