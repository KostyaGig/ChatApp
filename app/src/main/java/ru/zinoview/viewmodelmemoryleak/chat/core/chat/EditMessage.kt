package ru.zinoview.viewmodelmemoryleak.chat.core.chat

interface EditMessage {

    suspend fun editMessage(
        messageId: String,
        content: String
    )
}