package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.chat.DataMessage

interface CloudToDataMessageMapper : Mapper<DataMessage> {

    fun map(
        id: String = "",
        senderId: String = "-1",
        content: String = "",
        senderNickname: String = "",
        isRead: Boolean,
        isEdited: Boolean
    ) : DataMessage

    class Base(
        private val idSharedPreferences: IdSharedPreferences<String, Unit>
    ) : CloudToDataMessageMapper, Mapper.Base<DataMessage>(DataMessage.Empty) {

        override fun map(
            id: String,
            senderId: String,
            content: String,
            senderNickname: String,
            isRead: Boolean,
            isEdited: Boolean
        ): DataMessage {
            val prefId = idSharedPreferences.read(Unit)
            Log.d("zinoviewk","pref id $prefId, senderId $senderId, isRead $isRead, isEidted $isEdited")
            return if (prefId == senderId) {
                if (isRead) {
                    if (isEdited) {
                        Log.d("zinoviewk","SENT EDITED")
                        DataMessage.Sent.Read.Edited(id, senderId,content,  senderNickname)
                    } else {
                        DataMessage.Sent.Read.Base(id, senderId,content, senderNickname)
                    }
                } else {
                    if (isEdited) {
                        DataMessage.Sent.Unread.Edited(id, senderId,content,  senderNickname)
                    } else {
                        DataMessage.Sent.Unread.Base(id, senderId,content, senderNickname)
                    }
                }
            } else {
                if (isEdited) {
                    DataMessage.Received.Edited(id, senderId, content, senderNickname)
                } else {
                    DataMessage.Received.Base(id, senderId, content, senderNickname)
                }
            }
        }

        override fun mapFailure(message: String)
            = DataMessage.Failure(message)

        override fun mapProgress(senderId: String, content: String,senderNickname: String)
            = DataMessage.Progress(senderId, content,senderNickname)

        override fun mapIsTyping(senderNickname: String)
            = DataMessage.Typing.Is(senderNickname)

        override fun mapIsNotTyping(senderNickname: String)
            = DataMessage.Typing.IsNot(senderNickname)

    }
}