package ru.zinoview.viewmodelmemoryleak.chat.core.chat

interface Mapper<T> {

    fun map(
        id: String = "",
        senderId: Int = -1,
        content: String = "",
        senderNickname: String = ""
    ) : T

    fun mapFailure(message: String) : T

    fun mapReceived(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ): T

    fun mapSent(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) : T
}