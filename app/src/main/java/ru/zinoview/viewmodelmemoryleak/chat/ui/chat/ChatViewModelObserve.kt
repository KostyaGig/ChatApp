package ru.zinoview.viewmodelmemoryleak.chat.ui.chat

import ru.zinoview.viewmodelmemoryleak.chat.ui.core.CommunicationObserve

interface ChatViewModelObserve : CommunicationObserve<List<UiChatMessage>> {

    fun observeMessages()
}