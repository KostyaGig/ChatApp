package ru.zinoview.viewmodelmemoryleak.chat.ui.chat

import ru.zinoview.viewmodelmemoryleak.chat.core.chat.Mapper


class DataToUiMessageMapper : Mapper<UiChatMessage> {

    override fun mapSent(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ): UiChatMessage
        = UiChatMessage.Sent(
            id,content,senderId.toString(),senderNickname
        )

    override fun mapFailure(message: String): UiChatMessage
        = UiChatMessage.Failure(message)

    override fun mapReceived(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ): UiChatMessage
        = UiChatMessage.Sent(
            id,content,senderId.toString(),senderNickname
        )

    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = UiChatMessage.Empty

}