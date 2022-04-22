package ru.zinoview.viewmodelmemoryleak.ui.chat.state

interface UiStateMessageSession {

    fun map(mapper: ToUiStateMessageSessionMapper) : UiState.MessageSession
}