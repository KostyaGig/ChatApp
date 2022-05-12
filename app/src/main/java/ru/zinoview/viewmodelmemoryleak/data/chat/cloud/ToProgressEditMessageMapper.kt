package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import ru.zinoview.viewmodelmemoryleak.core.Time
import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper

class ToProgressEditMessageMapper(
    private val time: Time<String>
) : Mapper.Base<CloudMessage>(CloudMessage.Empty) {

    override fun map(
        id: String,
        senderId: String,
        content: String,
        senderNickname: String
    ): CloudMessage = CloudMessage.Progress.Edit(senderId,content,time.time())
}