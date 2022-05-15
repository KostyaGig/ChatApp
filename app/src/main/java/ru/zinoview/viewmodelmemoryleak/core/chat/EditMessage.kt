package ru.zinoview.viewmodelmemoryleak.core.chat

interface EditMessage {

    suspend fun editMessage(
        messageId: String,
        content: String,
        receiverId: String
    )
}