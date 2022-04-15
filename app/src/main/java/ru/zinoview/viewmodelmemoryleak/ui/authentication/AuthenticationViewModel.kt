package ru.zinoview.viewmodelmemoryleak.ui.authentication

import ru.zinoview.viewmodelmemoryleak.data.authentication.AuthenticationRepository
import ru.zinoview.viewmodelmemoryleak.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.CommunicationObserve

interface AuthenticationViewModel : CommunicationObserve<UiAuthentication> {

    fun auth()

    class Base(
        private val repository: AuthenticationRepository,
        private val communication: AuthenticationCommunication,
        private val mapper: DataToUiAuthMapper
    ) : AuthenticationViewModel, BaseViewModel<UiAuthentication>(
        repository,communication
    ) {

        override fun auth() {
            val uiAuth = repository.auth().map(mapper)
            communication.postValue(uiAuth)
        }

    }
}