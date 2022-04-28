package ru.zinoview.viewmodelmemoryleak.ui.chat.user_status


interface UiMessagesNotification {

    class Base(
        private val time: String,
        private val content: String,
    ) : UiMessagesNotification {

    }
}