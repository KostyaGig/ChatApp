package ru.zinoview.viewmodelmemoryleak.chat.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.zinoview.viewmodelmemoryleak.chat.data.chat.ChatRepository
import ru.zinoview.viewmodelmemoryleak.chat.data.connection.ConnectionRepository
import ru.zinoview.viewmodelmemoryleak.chat.ui.connection.ConnectionCommunication
import ru.zinoview.viewmodelmemoryleak.chat.ui.connection.DataToUiConnectionMapper
import ru.zinoview.viewmodelmemoryleak.chat.ui.connection.UiConnectionWrapper
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Dispatcher

interface ChatViewModelFactory : ViewModelProvider.Factory {

    class Base(
        private val repository: ChatRepository,
        private val connectionRepository: ConnectionRepository,
    ) : ChatViewModelFactory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val dispatcher = Dispatcher.Base()
            return ChatViewModel.Base(
                repository,
                dispatcher,
                DataToUiMessageMapper(),
                MessagesCommunication(),
                UiConnectionWrapper.Base(
                    dispatcher,
                    connectionRepository,
                    ConnectionCommunication(),
                    DataToUiConnectionMapper()
                )
            ) as T
        }

    }
}