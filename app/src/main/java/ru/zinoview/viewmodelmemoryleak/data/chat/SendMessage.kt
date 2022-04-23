package ru.zinoview.viewmodelmemoryleak.data.chat

interface SendMessage {

    suspend fun sendMessage(userId: String,content: String)
}