package ru.zinoview.viewmodelmemoryleak.ui.chat.edit

import ru.zinoview.viewmodelmemoryleak.ui.chat.UiMessage
import ru.zinoview.viewmodelmemoryleak.ui.core.ItemClickListener

interface EditMessageListener : ItemClickListener<UiMessage> {


    object Empty : EditMessageListener {
        override fun onClick(item: UiMessage) = Unit
    }
}