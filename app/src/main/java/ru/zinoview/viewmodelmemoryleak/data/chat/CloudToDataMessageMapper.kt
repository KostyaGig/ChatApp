package ru.zinoview.viewmodelmemoryleak.data.chat

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences

class CloudToDataMessageMapper(
    private val idSharedPreferences: IdSharedPreferences<Int,Unit>
) : Mapper<DataMessage> {

    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ): DataMessage {
        return if (idSharedPreferences.read(Unit) == senderId) {
            DataMessage.Sent(id, senderId, content, senderNickname)
        } else {
            DataMessage.Received(id, senderId, content, senderNickname)
        }
    }

    override fun mapFailure(message: String): DataMessage
        = DataMessage.Failure(message)

    override fun mapProgress(senderId: Int, content: String)
        = DataMessage.Progress(senderId, content)

    override fun mapReceived(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ): DataMessage = DataMessage.Empty

    override fun mapSent(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ): DataMessage = DataMessage.Empty

    class TestCloudToDataMessageMapper : Mapper<DataMessage> {
        override fun map(
            id: String,
            senderId: Int,
            content: String,
            senderNickname: String
        ): DataMessage {
            return if (senderId == 1) {
                DataMessage.Sent(id, senderId, content, senderNickname)
            } else {
                DataMessage.Received(id, senderId, content, senderNickname)
            }
        }

        override fun mapFailure(message: String): DataMessage
            = DataMessage.Failure(message)

        override fun mapProgress(senderId: Int, content: String)
            = DataMessage.Empty

        override fun mapReceived(
            id: String,
            senderId: Int,
            content: String,
            senderNickname: String
        ): DataMessage = DataMessage.Empty

        override fun mapSent(
            id: String,
            senderId: Int,
            content: String,
            senderNickname: String
        ): DataMessage  = DataMessage.Empty

    }
}