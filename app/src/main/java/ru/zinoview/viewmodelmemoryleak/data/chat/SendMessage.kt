package ru.zinoview.viewmodelmemoryleak.data.chat

interface SendMessage {

    suspend fun sendMessage(senderId: String, receiverId: String,nickName: String, content: String)
}