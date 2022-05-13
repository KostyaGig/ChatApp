package ru.zinoview.viewmodelmemoryleak.ui.chat

import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper

interface DomainToUiMessageMapper : Mapper<UiMessage> {

    class Base(
        private val resourceProvider: ResourceProvider
    ) : DomainToUiMessageMapper, Mapper.Base<UiMessage>(UiMessage.Empty) {
        override fun mapFailure(message: String): UiMessage
                = UiMessage.Failure(message)

        override fun mapReceived(
            id: String,
            senderId: String,
            content: String,
            senderNickname: String
        ): UiMessage
                = UiMessage.Received.Base(
            id,content, senderId,senderNickname
        )

        override fun mapProgress(senderId: String, content: String,senderNickname: String)
            = UiMessage.ProgressMessage(senderId,content,senderNickname)

        override fun mapRead(
            id: String,
            senderId: String,
            content: String,
            senderNickname: String
        ) = UiMessage.Sent.Read(id,content, senderId,senderNickname)

        override fun mapUnRead(
            id: String,
            senderId: String,
            content: String,
            senderNickname: String
        ) =  UiMessage.Sent.Unread(id,content,senderId,senderNickname)

        override fun mapIsTyping(senderNickname: String)
            = UiMessage.Typing.Is("$senderNickname $SUFFIX")

        override fun mapIsNotTyping(senderNickname: String)
            = UiMessage.Typing.IsNot(resourceProvider.string(R.string.app_name))

        private companion object {
            private const val SUFFIX = "is typing..."
        }
    }

}