package ru.zinoview.viewmodelmemoryleak.ui.chat.state

interface SaveState {

    fun saveState(viewModel: UiStateViewModel,editText: UiState.EditText)
}