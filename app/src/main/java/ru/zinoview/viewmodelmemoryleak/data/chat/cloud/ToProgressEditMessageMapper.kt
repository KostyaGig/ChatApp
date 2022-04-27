package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper

class ToProgressEditMessageMapper : Mapper.Base<CloudMessage>(CloudMessage.Empty) {

    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ): CloudMessage = CloudMessage.Progress.Edit(senderId.toString(),content)
}