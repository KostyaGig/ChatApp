package ru.zinoview.viewmodelmemoryleak.core.chat


interface ToTypeMessage<T> {

    suspend fun toTypeMessage(isTyping: Boolean) : T

    interface Unit : ToTypeMessage<kotlin.Unit>
}