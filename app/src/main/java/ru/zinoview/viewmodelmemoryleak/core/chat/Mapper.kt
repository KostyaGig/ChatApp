package ru.zinoview.viewmodelmemoryleak.core.chat

import android.util.Log

interface Mapper<T> {

    abstract class Base<T>(
        private val empty: T
    ): Mapper<T> {

        override fun map(id: String, senderId: Int, content: String, senderNickname: String)
             = empty

        override fun mapFailure(message: String)
             = empty

        override fun mapProgress(
            senderId: Int,
            content: String,
        )  = empty

        override fun mapRead(
            id: String,
            senderId: Int,
            content: String,
            senderNickname: String
        ) = empty

        override fun mapReceived(
            id: String,
            senderId: Int,
            content: String,
            senderNickname: String
        ) = empty

        override fun mapUnRead(
            id: String,
            senderId: Int,
            content: String,
            senderNickname: String
        ) = empty

        override fun mapIsNotTyping(senderNickname: String) = empty
        override fun mapIsTyping(senderNickname: String) = empty
    }

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

    fun mapRead(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) : T

    fun mapUnRead(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) : T

    fun mapIsTyping(
        senderNickname: String
    ) : T

    fun mapIsNotTyping(
        senderNickname: String
    ) : T
}