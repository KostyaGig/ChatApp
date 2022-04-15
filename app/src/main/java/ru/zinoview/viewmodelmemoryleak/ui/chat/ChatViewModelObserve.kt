package ru.zinoview.viewmodelmemoryleak.ui.chat

import ru.zinoview.viewmodelmemoryleak.ui.core.CommunicationObserve
import ru.zinoview.viewmodelmemoryleak.ui.core.ObserveConnection

interface ChatViewModelObserve : CommunicationObserve<List<UiChatMessage>>, ObserveConnection