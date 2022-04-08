package ru.zinoview.viewmodelmemoryleak.chat.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.zinoview.viewmodelmemoryleak.chat.data.authentication.AuthenticationRepository

interface AuthenticationViewModelFactory : ViewModelProvider.Factory {

    class Base(
        private val repository: AuthenticationRepository
    ) : AuthenticationViewModelFactory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T
            = AuthenticationViewModel.Base(
                repository,
            AuthenticationCommunication(),
            DataToUiAuthMapper()
        ) as T

    }
}