package ru.zinoview.viewmodelmemoryleak.ui.chat

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper

interface ToUiFoundMessageMapper : Mapper<UiMessage> {

    class Base : Mapper.Base<UiMessage>(UiMessage.Empty),ToUiFoundMessageMapper {

        override fun map(
            id: String,
            senderId: Int,
            content: String,
            senderNickname: String
        ): UiMessage = UiMessage.Received.Found(id,content,senderId.toString(),senderNickname)
    }
}