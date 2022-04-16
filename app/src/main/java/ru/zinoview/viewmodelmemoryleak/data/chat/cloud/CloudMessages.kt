package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.core.chat.Message

interface CloudMessage : Message {

    data class Base(
        private val id: String,
        private val senderId: Int,
        private val content: String,
        private val senderNickname: String
    ) : CloudMessage {

        override fun <T> map(mapper: Mapper<T>): T
            = mapper.map(id, senderId, content, senderNickname)
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

    data class Failure(
        private val message: String
    ) : CloudMessage {
        override fun <T> map(mapper: Mapper<T>): T
            = mapper.mapFailure(message)
    }

    class Progress(
        private val senderId: Int,
        private val content: String,
    ) : CloudMessage {

        override fun <T> map(mapper: Mapper<T>): T
            = mapper.mapProgress(senderId, content)
    }

    object Empty : CloudMessage {
        override fun <T> map(mapper: Mapper<T>): T
            = mapper.map()
    }
}

class Value(
    private val nameValuePairs: CloudMessage.Base
) : ru.zinoview.viewmodelmemoryleak.core.chat.Mapper<CloudMessage> {

    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = nameValuePairs

    override fun mapReceived(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ): CloudMessage = CloudMessage.Base(id, senderId, content, senderNickname)

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
}

