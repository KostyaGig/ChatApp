package ru.zinoview.viewmodelmemoryleak.core.chat

interface SendMessageWithSenderData {

    suspend fun sendMessage(userId: String, nickName: String, content: String)
}