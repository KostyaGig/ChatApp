package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.core.chat.Message
import ru.zinoview.viewmodelmemoryleak.data.chat.DataMessage
import ru.zinoview.viewmodelmemoryleak.ui.core.Same

interface CloudMessage : Message, Same<String,Unit> {

    override fun same(id: String, arg2: Unit): Boolean = false
    override fun <T> map(mapper: Mapper<T>): T = mapper.map()

    fun <T> map(content: String,mapper: Mapper<T>) : T = mapper.map()
    fun map(mapper: CloudToDataMessageMapper) : DataMessage = DataMessage.Empty

    fun addUnreadMessageId(userId: Int,unreadMessages: MutableList<String>) = Unit

    data class Base(
        private val id: String,
        private val senderId: Int,
        private val content: String,
        private val senderNickname: String,
        private val isRead: Boolean
    ) : CloudMessage {

        override fun <T> map(content: String, mapper: Mapper<T>): T
            = mapper.map(id, senderId, content, senderNickname)

        override fun map(mapper: CloudToDataMessageMapper)
            = mapper.map(id, senderId, content, senderNickname, isRead)

        override fun same(id: String, arg2: Unit) = this.id == id

        override fun addUnreadMessageId(userId: Int, unreadMessages: MutableList<String>) {
            if (isRead.not() && senderId != userId) {
                unreadMessages.add(id)
            }
        }
    }

    data class Failure(
        private val message: String
    ) : CloudMessage {

        override fun map(mapper: CloudToDataMessageMapper)
            = mapper.mapFailure(message)
    }

    class Progress(
        private val senderId: String,
        private val content: String,
    ) : CloudMessage {

        override fun map(mapper: CloudToDataMessageMapper)
            = mapper.mapProgress(senderId.toInt(), content)
    }

    data class Test(
        private val id: String,
        private val senderId: String,
        private val content: String,
        private val isRead: Boolean,
        private val senderNickname: String
    ) : CloudMessage {

        override fun <T> map(mapper: Mapper<T>): T
                = mapper.map(id, senderId.toInt(), content, senderNickname)

        override fun map(mapper: CloudToDataMessageMapper)
             = mapper.map(id, senderId.toInt(), content, senderNickname, isRead)

        fun update(content: String) = Test(id,senderId, content,    isRead ,senderNickname)

        fun read() = Test(id, senderId, content, true, senderNickname)
    }

    object Empty : CloudMessage {
        override fun <T> map(mapper: Mapper<T>): T
            = mapper.map()
    }
}

class Value(
    private val nameValuePairs: CloudMessage.Base
) : Mapper.Base<CloudMessage>(CloudMessage.Empty) {


    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = nameValuePairs

}

class WrapperMessages (
    private val values: ArrayList<Value>
) : Mapper.Base<List<CloudMessage>>(emptyList()) {

    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = values.map { value ->
        value.map(id, senderId, content, senderNickname)
    }
}

