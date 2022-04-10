package ru.zinoview.viewmodelmemoryleak.chat.ui.chat.edit

import ru.zinoview.viewmodelmemoryleak.chat.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.chat.ui.UiEditChatMessage

class UiToEditChatMessageMapper : Mapper<UiEditChatMessage> {

    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = UiEditChatMessage.Base(id)

    override fun mapFailure(message: String) = UiEditChatMessage.Empty

    override fun mapProgress(senderId: Int, content: String) = UiEditChatMessage.Empty

    override fun mapReceived(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = UiEditChatMessage.Empty

    override fun mapSent(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = UiEditChatMessage.Empty

}