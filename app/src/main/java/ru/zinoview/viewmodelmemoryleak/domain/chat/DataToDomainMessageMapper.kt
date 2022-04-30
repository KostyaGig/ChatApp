package ru.zinoview.viewmodelmemoryleak.domain.chat

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper


interface DataToDomainMessageMapper : Mapper<DomainMessage> {

    class Base : DataToDomainMessageMapper, Mapper.Base<DomainMessage>(DomainMessage.Empty) {
        override fun mapFailure(message: String): DomainMessage
                = DomainMessage.Failure(message)

        override fun mapReceived(
            id: String,
            senderId: Int,
            content: String,
            senderNickname: String
        ): DomainMessage
                = DomainMessage.Received(
            id,senderId,content,senderNickname
        )

        override fun mapProgress(senderId: Int, content: String)
                = DomainMessage.Progress(senderId,content)

        override fun mapRead(
            id: String,
            senderId: Int,
            content: String,
            senderNickname: String
        ) = DomainMessage.Sent.Read(id,senderId,content,senderNickname)

        override fun mapUnRead(
            id: String,
            senderId: Int,
            content: String,
            senderNickname: String
        ) =  DomainMessage.Sent.Unread(id,senderId,content,senderNickname)
    }

}