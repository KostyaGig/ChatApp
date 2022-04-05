package ru.zinoview.viewmodelmemoryleak.chat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.CloudDataSource

interface ChatViewModelFactory : ViewModelProvider.Factory {

    class Base(
        private val cloudDataSource: CloudDataSource
    ) : ChatViewModelFactory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T
            = ChatViewModel.Base(cloudDataSource,Dispatcher.Base()) as T

    }
}