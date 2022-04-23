package ru.zinoview.viewmodelmemoryleak.ui.chat.edit

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiChatMessage

class ToEditedMessageMapper : Mapper.Base<UiChatMessage.EditedMessage>(UiChatMessage.EditedMessage.Empty) {

    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = UiChatMessage.EditedMessage.Base(id)
}