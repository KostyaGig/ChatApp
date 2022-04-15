package ru.zinoview.viewmodelmemoryleak.data.chat

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences

class CloudToDataMessageMapper(
    private val idSharedPreferences: IdSharedPreferences
) : Mapper<DataMessage> {

    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ): DataMessage {
        return if (idSharedPreferences.read() == senderId) {
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
}