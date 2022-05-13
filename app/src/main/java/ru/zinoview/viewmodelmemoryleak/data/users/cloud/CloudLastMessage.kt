package ru.zinoview.viewmodelmemoryleak.data.users.cloud

import ru.zinoview.viewmodelmemoryleak.core.Mapper

interface CloudLastMessage : Mapper<Unit,String> {

    class Base(
        private val currentUserId: String,
        private val lastMessage: String?,
        private val lastMessageSenderNickName: String?,
        private val lastMessageSenderId: String?
    ) : CloudLastMessage {

        override fun map(src: Unit): String {
            return when {
                lastMessage == null -> START_CHATTING
                currentUserId == lastMessageSenderId -> YOUR_MESSAGE + lastMessage
                else -> "$lastMessageSenderNickName : $lastMessage"
            }
        }

        private companion object {
            private const val START_CHATTING = "Start chatting now!"
            private const val YOUR_MESSAGE = "You : "
        }

    }
}