package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import ru.zinoview.viewmodelmemoryleak.ui.core.Same

interface CloudSame : Same<String,Unit> {

    fun same(item: CloudMessage) : Boolean
    fun sameSenderId(senderId: String) : Boolean
    fun sameContent(content: String) : Boolean
}