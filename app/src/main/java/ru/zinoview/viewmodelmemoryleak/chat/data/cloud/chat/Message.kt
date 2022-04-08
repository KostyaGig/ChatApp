package ru.zinoview.viewmodelmemoryleak.chat.data.cloud.chat

import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.UiChatMessage

interface Message {

    fun <T> map(mapper: Mapper<T>) : T

    interface Mapper<T> {

        fun map(
            id: String = "",
            senderId: Int = -1,
            content: String = "",
            senderNickname: String = ""
        ) : T

        fun mapReceived(
            id: String,
            senderId: Int,
            content: String,
            senderNickname: String
        ): T

        fun mapSent(
            id: String,
            senderId: Int,
            content: String,
            senderNickname: String
        ) : T
    }
}