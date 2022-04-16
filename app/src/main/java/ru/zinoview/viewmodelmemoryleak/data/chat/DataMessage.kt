package ru.zinoview.viewmodelmemoryleak.data.chat

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.core.chat.Message

interface DataMessage : Message {

    override fun <T> map(mapper: Mapper<T>): T
        = mapper.map()

    object Empty : DataMessage

    data class Failure(
        private val message: String
    ) : DataMessage {
        override fun <T> map(mapper: Mapper<T>): T
            = mapper.mapFailure(message)
    }

    abstract class Message(
        private val id: String,
        private val senderId: Int,
        private val content: String,
        private val senderNickname: String
    ) : DataMessage {

        override fun <T> map(mapper: Mapper<T>): T
            = mapper.map(id, senderId, content, senderNickname)
    }

    data class Sent(
        private val id: String,
        private val senderId: Int,
        private val content: String,
        private val senderNickname: String
    ) : Message(id, senderId, content, senderNickname) {

        override fun <T> map(mapper: Mapper<T>): T
            = mapper.mapSent(id, senderId, content, senderNickname)
    }

    data class Received(
        private val id: String,
        private val senderId: Int,
        private val content: String,
        private val senderNickname: String
    ) : Message(id, senderId, content, senderNickname) {

        override fun <T> map(mapper: Mapper<T>): T
            = mapper.mapReceived(id, senderId, content, senderNickname)
    }

    class Progress(
        private val senderId: Int,
        private val content: String,
    ) : DataMessage {

        override fun <T> map(mapper: Mapper<T>): T
            = mapper.mapProgress(senderId, content)
    }
}