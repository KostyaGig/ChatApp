package ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper

class ToUiStateMessageSessionMapper : Mapper.Base<ChatUiState>(ChatUiState.Empty) {
    override fun map(
        id: String,
        senderId: String,
        content: String,
        senderNickname: String
    ) = ChatUiState.MessageSession(content,id)
}