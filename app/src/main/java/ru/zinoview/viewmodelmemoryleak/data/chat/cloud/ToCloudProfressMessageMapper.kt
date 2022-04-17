package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper

class ToCloudProfressMessageMapper : Mapper<CloudMessage> {

    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = CloudMessage.Progress(senderId, content)

    override fun mapFailure(message: String) = CloudMessage.Empty
    override fun mapProgress(senderId: Int, content: String) = CloudMessage.Empty

    override fun mapReceived(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = CloudMessage.Empty
    override fun mapSent(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = CloudMessage.Empty
}