package ru.zinoview.viewmodelmemoryleak.chat.ui

import ru.zinoview.viewmodelmemoryleak.chat.ChatAdapter
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Observer

class UiChatMessagesObserver(
    private val adapter: ChatAdapter
) : Observer<List<UiChatMessage>> {

    override fun update(data: List<UiChatMessage>) {
//        adapter.tap()
        adapter.updateList(data)
    }
}