package ru.zinoview.viewmodelmemoryleak.ui.chat.state

interface ToUiStateMessageSessionMapper {

    fun map(oldMessageText: String, id: String) : UiState.MessageSession

    class Base : ToUiStateMessageSessionMapper {

        override fun map(oldMessageText: String, id: String)
            = UiState.MessageSession(oldMessageText,id)
    }
}