package ru.zinoview.viewmodelmemoryleak.ui.chat

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper


class DomainToUiMessageMapper : Mapper.Base<UiMessage>(
    UiMessage.Empty
) {


    override fun mapFailure(message: String): UiMessage
        = UiMessage.Failure(message)

    override fun mapReceived(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ): UiMessage
        = UiMessage.Received(
            id,content,senderId.toString(),senderNickname
        )

    override fun mapProgress(senderId: Int, content: String)
        = UiMessage.ProgressMessage(senderId.toString(),content)

    override fun mapRead(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = UiMessage.Sent.Read(id,content,senderId.toString(),senderNickname)

    override fun mapUnRead(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) =  UiMessage.Sent.Unread(id,content,senderId.toString(),senderNickname)

}