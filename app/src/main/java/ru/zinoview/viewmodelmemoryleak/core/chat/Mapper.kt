package ru.zinoview.viewmodelmemoryleak.core.chat

interface Mapper<T> {

    fun map(
        id: String = "",
        senderId: Int = -1,
        content: String = "",
        senderNickname: String = ""
    ) : T

    fun mapFailure(message: String) : T

    fun mapProgress(
        senderId: Int = -1,
        content: String = "",
    ) : T

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