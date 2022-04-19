package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.chat.DataMessage

interface CloudToDataMessageMapper : Mapper<DataMessage> {

    fun map(
        id: String = "",
        senderId: Int = -1,
        content: String = "",
        senderNickname: String = "",
        isRead: Boolean
    ) : DataMessage

    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = DataMessage.Empty

    override fun mapRead(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = DataMessage.Empty

    override fun mapSent(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = DataMessage.Empty

    override fun mapReceived(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = DataMessage.Empty

    override fun mapUnRead(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = DataMessage.Empty

    class Base(
        private val idSharedPreferences: IdSharedPreferences<Int, Unit>
    ) : CloudToDataMessageMapper {

        override fun map(
            id: String,
            senderId: Int,
            content: String,
            senderNickname: String,
            isRead: Boolean
        ): DataMessage {
            return if (idSharedPreferences.read(Unit) == senderId) {
                if (isRead) {
                    DataMessage.Sent.Read(id, senderId, content, senderNickname)
                } else {
                    DataMessage.Sent.Unread(id, senderId, content, senderNickname)
                }
            } else {
                DataMessage.Received(id, senderId, content, senderNickname)
            }
        }

        override fun mapFailure(message: String)
            = DataMessage.Failure(message)

        override fun mapProgress(senderId: Int, content: String) : DataMessage {
            Log.d("zinoviewk","map progress cloud to data")
            return DataMessage.Progress(senderId, content)
        }

    }
}