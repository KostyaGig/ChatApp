package ru.zinoview.viewmodelmemoryleak.core.chat

interface SendMessage {

    suspend fun sendMessage(receiverId: String,content: String)
}