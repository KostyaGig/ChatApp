package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

interface CloudUnreadMessageIds {

    data class Base(
        private val ids: List<String>,
        private val senderId: String,
        private val receiverId: String
    ) : CloudUnreadMessageIds
}