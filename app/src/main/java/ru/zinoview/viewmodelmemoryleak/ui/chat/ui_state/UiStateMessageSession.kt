package ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state

interface UiStateMessageSession {

    fun map(mapper: ToUiStateMessageSessionMapper) : ChatUiState.MessageSession
}