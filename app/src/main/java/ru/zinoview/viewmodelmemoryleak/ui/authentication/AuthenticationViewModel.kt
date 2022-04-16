package ru.zinoview.viewmodelmemoryleak.ui.authentication

import android.util.Log
import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.data.authentication.AuthenticationRepository
import ru.zinoview.viewmodelmemoryleak.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.CommunicationObserve

interface AuthenticationViewModel : CommunicationObserve<UiAuth> {

    fun auth()

    class Base(
        private val repository: AuthenticationRepository,
        private val worker: AuthWorker,
        private val communication: AuthenticationCommunication
    ) : AuthenticationViewModel, BaseViewModel<UiAuth>(
        repository,communication
    ) {

        override fun auth() = worker.execute(viewModelScope,{
            repository.auth()
        },{ uiAuth ->
            Log.d("zinoviewk","auth view model $uiAuth")
            communication.postValue(uiAuth)
        })
    }
}