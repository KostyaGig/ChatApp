package ru.zinoview.viewmodelmemoryleak.chat.ui.chat.edit

import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.UiChatMessage

interface EditMessageListener {

    fun edit(message: UiChatMessage)

    object Empty : EditMessageListener {
        override fun edit(message: UiChatMessage) = Unit
    }
}