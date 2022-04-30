package ru.zinoview.viewmodelmemoryleak.ui.chat.edit

import ru.zinoview.viewmodelmemoryleak.ui.chat.UiMessage

interface EditMessageListener {

    fun edit(message: UiMessage)

    object Empty : EditMessageListener {
        override fun edit(message: UiMessage) = Unit
    }
}