package ru.zinoview.viewmodelmemoryleak.chat.data.cloud.chat

import ru.zinoview.viewmodelmemoryleak.chat.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.chat.core.chat.Message

interface DataMessage : Message {

    override fun <T> map(mapper: Mapper<T>): T
        = mapper.map()

    object Empty : DataMessage

    abstract class Message(
        private val id: String,
        private val senderId: Int,
        private val content: String,
        private val senderNickname: String
    ) : DataMessage {

        override fun <T> map(mapper: Mapper<T>): T
            = mapper.map(id, senderId, content, senderNickname)
    }

    class Sent(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) : Message(id, senderId, content, senderNickname)

    class Received(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) : Message(id, senderId, content, senderNickname)
}
