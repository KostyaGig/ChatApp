package ru.zinoview.viewmodelmemoryleak.core.chat

interface ShowNotificationMessage {

    suspend fun showNotificationMessage(messageId: String)
}