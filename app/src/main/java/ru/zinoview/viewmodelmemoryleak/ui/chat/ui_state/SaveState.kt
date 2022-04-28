package ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state

interface SaveState {

    fun saveState(viewModel: UiStateViewModel,editText: UiState.EditText)
}