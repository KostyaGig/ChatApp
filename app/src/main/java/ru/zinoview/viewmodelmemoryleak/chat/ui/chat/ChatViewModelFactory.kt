package ru.zinoview.viewmodelmemoryleak.chat.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.chat.ChatRepository
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Dispatcher

interface ChatViewModelFactory : ViewModelProvider.Factory {

    class Base(
        private val repository: ChatRepository
    ) : ChatViewModelFactory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T
            = ChatViewModel.Base(repository, Dispatcher.Base(),DataToUiMessageMapper(),MessagesCommunication()) as T

    }
}