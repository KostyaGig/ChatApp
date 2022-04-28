package ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiChatMessage

class ToOldMessageMapper : Mapper.Base<UiChatMessage.OldMessage>(UiChatMessage.OldMessage.Empty) {

    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = UiChatMessage.OldMessage.Base(id,content)

}