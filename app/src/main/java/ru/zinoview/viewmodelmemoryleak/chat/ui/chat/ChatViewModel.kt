package ru.zinoview.viewmodelmemoryleak.chat.ui.chat

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.chat.core.Clean
import ru.zinoview.viewmodelmemoryleak.chat.data.chat.ChatRepository
import ru.zinoview.viewmodelmemoryleak.chat.data.connection.ConnectionRepository
import ru.zinoview.viewmodelmemoryleak.chat.ui.connection.ConnectionCommunication
import ru.zinoview.viewmodelmemoryleak.chat.ui.connection.DataToUiConnectionMapper
import ru.zinoview.viewmodelmemoryleak.chat.ui.connection.UiConnection
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.ActionViewModel
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Dispatcher

interface ChatViewModel : ChatViewModelObserve, Clean, ActionViewModel<String> {

    fun messages()

    fun connection()

    // todo move to another class
    class Base(
        private val repository: ChatRepository,
        private val dispatcher: Dispatcher,
        private val mapper: DataToUiMessageMapper,
        private val communication: MessagesCommunication,
        private val connectionRepository: ConnectionRepository,
        private val connectionCommunication: ConnectionCommunication,
        private val connectionMapper: DataToUiConnectionMapper
    ) : BaseViewModel<List<UiChatMessage>>(repository,communication), ChatViewModel {

        override fun doAction(content: String)
            = repository.sendMessage(content)

        override fun messages() {
            communication.postValue(listOf(UiChatMessage.Progress))
            dispatcher.doBackground(viewModelScope) {
                repository.messages { data ->
                    val ui = data.map { it.map(mapper) }
                    dispatcher.doUi(viewModelScope) {
                        communication.postValue(ui)
                    }
                }
            }
        }

        override fun observeConnection(owner: LifecycleOwner,observer: Observer<UiConnection>)
            = connectionCommunication.observe(owner, observer)

        override fun connection() {
            dispatcher.doBackground(viewModelScope) {
                connectionRepository.observe { data ->
                    val ui = data.map(connectionMapper)
                    dispatcher.doUi(viewModelScope) {
                        connectionCommunication.postValue(ui)
                    }
                }
            }
        }

    }
}