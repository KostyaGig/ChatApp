package ru.zinoview.viewmodelmemoryleak.data.chat

interface SendMessage {

    suspend fun sendMessage(userId: String,nickName: String,  content: String)
}