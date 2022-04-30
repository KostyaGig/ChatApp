package ru.zinoview.viewmodelmemoryleak.core.chat

import ru.zinoview.viewmodelmemoryleak.data.chat.DataMessage

interface Messages<T> {

    suspend fun messages(block: (List<T>) -> Unit)
}