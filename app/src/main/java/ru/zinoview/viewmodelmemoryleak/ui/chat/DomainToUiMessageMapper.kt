package ru.zinoview.viewmodelmemoryleak.ui.chat

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.domain.chat.DomainMessage


interface DomainToUiMessageMapper : Mapper<UiMessage> {

    class Base : DomainToUiMessageMapper, Mapper.Base<UiMessage>(UiMessage.Empty) {
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

        override fun mapIsTyping(senderNickname: String)
            = UiMessage.Typing.Is(senderNickname)

        override fun mapIsNotTyping(senderNickname: String)
            = UiMessage.Typing.IsNot(senderNickname)
    }

}