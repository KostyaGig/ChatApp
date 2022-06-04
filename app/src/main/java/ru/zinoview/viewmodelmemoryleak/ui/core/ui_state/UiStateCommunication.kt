package ru.zinoview.viewmodelmemoryleak.ui.core.ui_state

import ru.zinoview.viewmodelmemoryleak.ui.core.Communication

class UiStateCommunication : Communication.Base<List<UiState>>() {

    override fun postValue(value: List<UiState>) {
        super.postValue(value)
        super.postValue(emptyList())
    }
}