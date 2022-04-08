package ru.zinoview.viewmodelmemoryleak.chat.ui.join

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.join.JoinUserRepository
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Dispatcher

interface JoinUserViewModelFactory : ViewModelProvider.Factory {

    class Base(
        private val repository: JoinUserRepository
    ) : JoinUserViewModelFactory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T
            = JoinUserViewModel.Base(repository, Dispatcher.Base()) as T
    }
}