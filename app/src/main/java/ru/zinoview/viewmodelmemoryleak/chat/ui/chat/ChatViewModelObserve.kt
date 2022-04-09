package ru.zinoview.viewmodelmemoryleak.chat.ui.chat

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.zinoview.viewmodelmemoryleak.chat.ui.connection.UiConnection
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.CommunicationObserve

interface ChatViewModelObserve : CommunicationObserve<List<UiChatMessage>> {

    fun observeConnection(owner: LifecycleOwner, observer: Observer<UiConnection>)
}