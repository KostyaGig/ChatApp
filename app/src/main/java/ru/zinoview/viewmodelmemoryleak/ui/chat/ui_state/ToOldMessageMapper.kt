package ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiMessage

class ToOldMessageMapper : Mapper.Base<UiMessage.OldMessage>(UiMessage.OldMessage.Empty) {

    override fun map(
        id: String,
        senderId: String,
        content: String,
        senderNickname: String
    ) = UiMessage.OldMessage.Base(id,content)

}