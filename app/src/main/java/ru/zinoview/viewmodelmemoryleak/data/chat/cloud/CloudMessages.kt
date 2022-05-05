package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.core.chat.Message
import ru.zinoview.viewmodelmemoryleak.data.chat.DataMessage
import ru.zinoview.viewmodelmemoryleak.ui.chat.notification.NotificationMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.notification.NotificationWrapper

interface CloudMessage : Message, CloudSame {

    override fun same(item: CloudMessage) : Boolean = false
    override fun same(id: String, arg2: Unit): Boolean = false
    override fun sameSenderId(senderId: String): Boolean = false
    override fun sameContent(content: String) : Boolean = false
    override fun <T> map(mapper: Mapper<T>): T = mapper.map()

    fun <T> map(content: String,mapper: Mapper<T>) : T = mapper.map()
    fun map(mapper: CloudToDataMessageMapper) : DataMessage = DataMessage.Empty

    fun mapNotification(mapper: NotificationMapper) : NotificationWrapper = NotificationWrapper.Empty

    fun addUnreadMessageId(userId: String,unreadMessages: MutableList<String>) = Unit

    data class Base(
        private val id: String,
        private val senderId: Int,
        private val content: String,
        private val senderNickName: String,
        private val isRead: Boolean,
    ) : CloudMessage {

        override fun <T> map(content: String, mapper: Mapper<T>): T
            = mapper.map(id, senderId, content, senderNickName)

        override fun map(mapper: CloudToDataMessageMapper)
            = mapper.map(id, senderId, content, senderNickName, isRead)

        override fun same(item: CloudMessage) = item.sameSenderId(senderId.toString())
        override fun sameSenderId(senderId: String) = this.senderId.toString() == senderId
        override fun same(id: String, arg2: Unit) = this.id == id

        override fun sameContent(content: String)
            = this.content == content

        override fun addUnreadMessageId(userId: String, unreadMessages: MutableList<String>) {
            if (isRead.not() && senderId.toString() != userId) {
                unreadMessages.add(id)
            }
        }
    }

    data class Typing(
        private val senderNickName: String,
        private val isTyping: Boolean,
        private val isCloud: Boolean = false
    ) : CloudMessage {


        override fun map(mapper: CloudToDataMessageMapper) : DataMessage {
            return if (isTyping) {
                mapper.mapIsTyping(senderNickName)
            } else {
                mapper.mapIsNotTyping(senderNickName)
            }
        }

        fun isCloud() = isCloud
    }

    data class Failure(
        private val message: String
    ) : CloudMessage {

        override fun map(mapper: CloudToDataMessageMapper)
            = mapper.mapFailure(message)
    }

    interface Progress : CloudMessage {

        abstract class Base(
            private val senderId: String,
            private val content: String,
        ) : Progress {

            override fun map(mapper: CloudToDataMessageMapper)
                = mapper.mapProgress(senderId.toInt(), content)

            override fun same(item: CloudMessage) = item.sameContent(content)

            override fun <T> map(mapper: Mapper<T>)
                = mapper.map(content = content)
        }

        class Send(
            senderId: String,
            private val content: String,
            private val time: String
        ) : Base(senderId, content) {

            override fun mapNotification(mapper: NotificationMapper)
                = mapper.mapSend(content,time)
        }

        data class Edit(
            private val senderId: String,
            private val content: String,
            private val time: String
        ) : Base(senderId, content) {

            override fun mapNotification(mapper: NotificationMapper)
                = mapper.mapEdit(content,time)
        }

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

        fun updated(content: String) = Test(id,senderId, content,isRead ,senderNickname)

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

class TypingValue(
    private val nameValuePairs: CloudMessage.Typing
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

