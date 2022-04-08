package ru.zinoview.viewmodelmemoryleak.chat.data.cloud.chat

import ru.zinoview.viewmodelmemoryleak.chat.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences

class CloudToDataMessageMapper(
    private val idSharedPreferences: IdSharedPreferences
) : Mapper<DataMessage> {

    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ): DataMessage {
        return if (idSharedPreferences.read().toString() == id) {
            DataMessage.Sent(id, senderId, content, senderNickname)
        } else {
            DataMessage.Received(id, senderId, content, senderNickname)
        }
    }

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