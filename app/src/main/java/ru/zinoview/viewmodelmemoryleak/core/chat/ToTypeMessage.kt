package ru.zinoview.viewmodelmemoryleak.core.chat


interface ToTypeMessage {

    suspend fun toTypeMessage(isTyping: Boolean)
}