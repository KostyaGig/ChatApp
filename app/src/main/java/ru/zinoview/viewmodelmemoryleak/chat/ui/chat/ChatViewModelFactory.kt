package ru.zinoview.viewmodelmemoryleak.chat.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Dispatcher

interface ChatViewModelFactory : ViewModelProvider.Factory {

    class Base(
        private val cloudDataSource: CloudDataSource.SendMessage
    ) : ChatViewModelFactory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T
            = ChatViewModel.Base(cloudDataSource, Dispatcher.Base()) as T

    }
}