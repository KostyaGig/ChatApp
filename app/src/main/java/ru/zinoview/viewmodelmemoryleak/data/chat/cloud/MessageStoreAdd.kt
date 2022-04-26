package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import ru.zinoview.viewmodelmemoryleak.core.Add

interface MessageStoreAdd : Add<CloudMessage> {

    fun addMessages(messages: List<CloudMessage>)
}