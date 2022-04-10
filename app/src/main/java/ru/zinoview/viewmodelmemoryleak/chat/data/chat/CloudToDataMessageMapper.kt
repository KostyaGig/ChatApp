package ru.zinoview.viewmodelmemoryleak.chat.data.chat

import android.util.Log
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
        return if (idSharedPreferences.read() == senderId) {
            Log.d("zinoviewk","DATA MSG map to Sent")
            DataMessage.Sent(id, senderId, content, senderNickname)
        } else {
            Log.d("zinoviewk","DATA MSG map to RECEIVED")
            DataMessage.Received(id, senderId, content, senderNickname)
        }
    }

    override fun mapFailure(message: String): DataMessage
        = DataMessage.Failure(message)

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