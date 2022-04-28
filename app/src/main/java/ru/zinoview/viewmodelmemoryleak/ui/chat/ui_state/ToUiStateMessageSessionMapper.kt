package ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper

class ToUiStateMessageSessionMapper : Mapper.Base<UiState>(UiState.Empty) {
    override fun map(
        id: String,
        senderId: Int,
        content: String,
        senderNickname: String
    ) = UiState.MessageSession(content,id)
}