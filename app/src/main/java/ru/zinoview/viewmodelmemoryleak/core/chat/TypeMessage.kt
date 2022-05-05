package ru.zinoview.viewmodelmemoryleak.core.chat


interface TypeMessage {

    suspend fun updateTypeMessageState(isTyping: Boolean)
}