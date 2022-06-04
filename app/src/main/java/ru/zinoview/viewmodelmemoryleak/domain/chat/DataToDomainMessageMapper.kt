package ru.zinoview.viewmodelmemoryleak.domain.chat

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper


interface DataToDomainMessageMapper : Mapper<DomainMessage> {

    class Base : DataToDomainMessageMapper, Mapper.Base<DomainMessage>(DomainMessage.Empty) {
        override fun mapFailure(message: String): DomainMessage
                = DomainMessage.Failure(message)

        override fun mapReceived(
            id: String,
            senderId: String,
            content: String,
            senderNickname: String
        ) = DomainMessage.Received.Base(id,senderId,content,senderNickname)

        override fun mapReceivedEdited(
            id: String,
            senderId: String,
            content: String,
            senderNickname: String
        ) = DomainMessage.Received.Edited(id,senderId,content,senderNickname)

        override fun mapProgress(senderId: String, content: String,senderNickname: String)
            = DomainMessage.Progress(senderId,content,senderNickname)

        override fun mapReadEdited(
            id: String,
            senderId: String,
            content: String,
            senderNickname: String
        ) = DomainMessage.Sent.Read.Edited(id,senderId,content,senderNickname)

        override fun mapRead(
            id: String,
            senderId: String,
            content: String,
            senderNickname: String
        ) = DomainMessage.Sent.Read.Base(id,senderId,content,senderNickname)

        override fun mapUnRead(
            id: String,
            senderId: String,
            content: String,
            senderNickname: String
        ) = DomainMessage.Sent.Unread.Base(id,senderId,content,senderNickname)

        override fun mapUnReadEdited(
            id: String,
            senderId: String,
            content: String,
            senderNickname: String
        ) = DomainMessage.Sent.Unread.Edited(id,senderId,content,senderNickname)

        override fun mapIsTyping(senderNickname: String)
            = DomainMessage.Typing.Is(senderNickname)

        override fun mapIsNotTyping(senderNickname: String)
            = DomainMessage.Typing.IsNot(senderNickname)
    }

}