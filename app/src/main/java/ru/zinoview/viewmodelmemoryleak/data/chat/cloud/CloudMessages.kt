package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import android.util.Log
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
        private val senderId: Int,
        private val content: String,
    ) : CloudMessage {

        override fun map(mapper: CloudToDataMessageMapper)
            = mapper.mapProgress(senderId, content)
    }

    data class Test(
        private val id: String,
        private val senderId: Int,
        private val content: String,
        private val senderNickname: String
    ) : CloudMessage {

        override fun <T> map(mapper: Mapper<T>): T
                = mapper.map(id, senderId, content, senderNickname)

        fun update(content: String) = Test(id,senderId, content, senderNickname)
    }

    object Empty : CloudMessage {
        override fun <T> map(mapper: Mapper<T>): T
            = mapper.map()
    }
}

class Value(
    private val nameValuePairs: CloudMessage.Base
) : Mapper<CloudMessage> {


    // todo move unused methods
    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = nameValuePairs

    override fun mapRead(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    )= CloudMessage.Empty

    override fun mapUnRead(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ): CloudMessage = CloudMessage.Empty

    override fun mapReceived(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String,
    ): CloudMessage = CloudMessage.Empty

    override fun mapSent(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ): CloudMessage = CloudMessage.Empty

    override fun mapFailure(message: String) = CloudMessage.Empty

    override fun mapProgress(
        senderId: Int,
        content: String,
    ): CloudMessage = CloudMessage.Empty

}

class WrapperMessages (
    private val values: ArrayList<Value>
) : Mapper<List<CloudMessage>> {

    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = values.map { value ->
        value.map(id, senderId, content, senderNickname)
    }

    override fun mapReceived(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ): List<CloudMessage> = emptyList()

    override fun mapSent(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ): List<CloudMessage> = emptyList()

    override fun mapProgress(
        senderId: Int,
        content: String,
    ) : List<CloudMessage> = emptyList()

    override fun mapFailure(message: String) : List<CloudMessage> = emptyList()
    override fun mapRead(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ): List<CloudMessage> = emptyList()

    override fun mapUnRead(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ): List<CloudMessage> {
        TODO("Not yet implemented")
    }
}

