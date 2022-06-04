package ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state

import ru.zinoview.viewmodelmemoryleak.ui.users.BundleUser

interface SaveState {

    fun saveState(viewModel: ChatUiStateViewModel, editText: ChatUiState.EditText,bundleUser: BundleUser)
}