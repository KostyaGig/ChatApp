package ru.zinoview.viewmodelmemoryleak.domain.chat

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.core.chat.Message
import ru.zinoview.viewmodelmemoryleak.data.chat.DataMessage

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
        private val senderId: String,
        private val content: String,
        private val senderNickname: String
    ) : DomainMessage {

        override fun <T> map(mapper: Mapper<T>): T
            = mapper.map(id, senderId, content, senderNickname)
    }

    abstract class Sent(
        id: String,
        senderId: String,
        content: String,
        senderNickname: String
    ) : Message(id, senderId, content, senderNickname) {


        abstract class Read(
            id: String,
            senderId: String,
            content: String,
            senderNickname: String
        ) : Sent(id, senderId, content, senderNickname) {

            data class Edited(
                private val id: String,
                private val senderId: String,
                private val content: String,
                private val senderNickname: String
            ) : Read(id, senderId, content, senderNickname) {

                override fun <T> map(mapper: Mapper<T>): T
                        = mapper.mapReceivedEdited(id, senderId, content, senderNickname)
            }

            data class Base(
                private val id: String,
                private val senderId: String,
                private val content: String,
                private val senderNickname: String
            ) : Read(id, senderId, content, senderNickname) {

                override fun <T> map(mapper: Mapper<T>): T
                    = mapper.mapRead(id, senderId, content, senderNickname)
            }
        }



        abstract class Unread(
            id: String,
            senderId: String,
            content: String,
            senderNickname: String
        ) : Sent(id, senderId, content, senderNickname) {

            data class Edited(
                private val id: String,
                private val senderId: String,
                private val content: String,
                private val senderNickname: String
            ) : Unread(id, senderId, content, senderNickname) {
                override fun <T> map(mapper: Mapper<T>): T
                        = mapper.mapUnReadEdited(id, senderId, content, senderNickname)
            }

            data class Base(
                private val id: String,
                private val senderId: String,
                private val content: String,
                private val senderNickname: String
            ) : Unread(id, senderId, content, senderNickname) {
                override fun <T> map(mapper: Mapper<T>): T
                        = mapper.mapUnRead(id, senderId, content, senderNickname)
            }
        }
    }

    abstract class Received(
        id: String,
        senderId: String,
        content: String,
        senderNickname: String
    ) : Message(id, senderId, content, senderNickname) {

        class Edited(
            private val id: String,
            private val senderId: String,
            private val content: String,
            private val senderNickname: String
        ) :Received(id, senderId, content, senderNickname) {
            override fun <T> map(mapper: Mapper<T>): T
                    = mapper.mapReceivedEdited(id, senderId, content, senderNickname)
        }

        class Base(
            private val id: String,
            private val senderId: String,
            private val content: String,
            private val senderNickname: String
        ) : Received(id, senderId, content, senderNickname) {

            override fun <T> map(mapper: Mapper<T>): T
                    = mapper.mapReceived(id, senderId, content, senderNickname)
        }
    }

    class Progress(
        private val senderId: String,
        private val content: String,
        private val senderNickname: String,
    ) : DomainMessage {

        override fun <T> map(mapper: Mapper<T>): T
            = mapper.mapProgress(senderId, content,senderNickname)
    }

    abstract class Typing : DomainMessage {

        data class Is(
            private val senderNickName: String
        ) : Typing() {

            override fun <T> map(mapper: Mapper<T>)
                = mapper.mapIsTyping(senderNickName)
        }

        data class IsNot(
            private val senderNickName: String
        ) : Typing() {

            override fun <T> map(mapper: Mapper<T>)
                = mapper.mapIsNotTyping(senderNickName)
        }

    }
}
