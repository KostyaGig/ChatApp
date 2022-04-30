package ru.zinoview.viewmodelmemoryleak.domain.chat

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.core.chat.Message

interface DomainMessage : Message {

    override fun <T> map(mapper: Mapper<T>): T
        = mapper.map()

    object Empty : DomainMessage

    data class Failure(
        private val message: String
    ) : DomainMessage {
        override fun <T> map(mapper: Mapper<T>): T
            = mapper.mapFailure(message)
    }

    abstract class Message(
        private val id: String,
        private val senderId: Int,
        private val content: String,
        private val senderNickname: String
    ) : DomainMessage {

        override fun <T> map(mapper: Mapper<T>): T
            = mapper.map(id, senderId, content, senderNickname)
    }

    abstract class Sent(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) : Message(id, senderId, content, senderNickname) {


        data class Read(
            private val id: String,
            private val senderId: Int,
            private val content: String,
            private val senderNickname: String
        ) : Sent(id, senderId, content, senderNickname) {

            override fun <T> map(mapper: Mapper<T>): T
                = mapper.mapRead(id, senderId, content, senderNickname)
        }

        data class Unread(
            private val id: String,
            private val senderId: Int,
            private val content: String,
            private val senderNickname: String
        ) : Sent(id, senderId, content, senderNickname) {

            override fun <T> map(mapper: Mapper<T>): T
                = mapper.mapUnRead(id, senderId, content, senderNickname)
        }
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
    ) : DomainMessage {

        override fun <T> map(mapper: Mapper<T>): T
            = mapper.mapProgress(senderId, content)
    }
}
