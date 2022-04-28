package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

interface DisconnectNotification {

    fun disconnect(service: NotificationService, content: String)
}