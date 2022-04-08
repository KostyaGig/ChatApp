package ru.zinoview.viewmodelmemoryleak.chat.data.cloud.chat

interface CloudMessage : Message{

    class Base(
        private val id: String,
        private val senderId: Int,
        private val content: String,
        private val senderNickname: String
    ) : CloudMessage {

        override fun <T> map(mapper: Message.Mapper<T>): T
            = mapper.map(id, senderId, content, senderNickname)
    }
}

class Value(
    private val nameValuePairs: CloudMessage
) : Message.Mapper<CloudMessage> {

    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = nameValuePairs
}

class WrapperMessages (
    private val values: ArrayList<Value>
) : Message.Mapper<List<CloudMessage>> {

    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = values.map { value ->
        value.map(id, senderId, content, senderNickname)
    }

}

