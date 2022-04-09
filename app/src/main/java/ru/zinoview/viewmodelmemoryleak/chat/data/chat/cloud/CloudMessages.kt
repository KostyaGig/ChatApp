package ru.zinoview.viewmodelmemoryleak.chat.data.chat.cloud

import ru.zinoview.viewmodelmemoryleak.chat.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.chat.core.chat.Message

interface CloudMessage : Message {

    class Base(
        private val id: String,
        private val senderId: Int,
        private val content: String,
        private val senderNickname: String
    ) : CloudMessage {

        override fun <T> map(mapper: Mapper<T>): T
            = mapper.map(id, senderId, content, senderNickname)
    }

    class Failure(
        private val message: String
    ) : CloudMessage {
        override fun <T> map(mapper: Mapper<T>): T
            = mapper.mapFailure(message)
    }

    object Empty : CloudMessage {
        override fun <T> map(mapper: Mapper<T>): T
            = mapper.map()
    }
}

class Value(
    private val nameValuePairs: CloudMessage.Base
) : Mapper<CloudMessage> {

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

    override fun mapFailure(message: String) : List<CloudMessage> = emptyList()
}

