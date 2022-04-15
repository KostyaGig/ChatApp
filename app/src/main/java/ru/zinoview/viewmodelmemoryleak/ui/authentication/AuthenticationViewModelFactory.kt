package ru.zinoview.viewmodelmemoryleak.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.zinoview.viewmodelmemoryleak.data.authentication.AuthenticationRepository

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