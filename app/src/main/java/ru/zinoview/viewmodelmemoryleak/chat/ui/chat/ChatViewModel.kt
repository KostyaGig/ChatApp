package ru.zinoview.viewmodelmemoryleak.chat.ui.chat

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.chat.core.Clean
import ru.zinoview.viewmodelmemoryleak.chat.data.chat.ChatRepository
import ru.zinoview.viewmodelmemoryleak.chat.ui.connection.UiConnection
import ru.zinoview.viewmodelmemoryleak.chat.ui.connection.UiConnectionWrapper
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.ActionViewModel
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Connection
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Dispatcher

interface ChatViewModel : ChatViewModelObserve, Clean, ActionViewModel<String>, Connection {

    fun messages()

    fun editMessage(messageId: String, content: String)

    class Base(
        private val repository: ChatRepository,
        private val dispatcher: Dispatcher,
        private val mapper: DataToUiMessageMapper,
        private val communication: MessagesCommunication,
        private val connectionWrapper: UiConnectionWrapper
    ) : BaseViewModel<List<UiChatMessage>>(repository,communication), ChatViewModel {

        override fun doAction(content: String) {
            dispatcher.doBackground(viewModelScope) {
                repository.sendMessage(content)
            }
        }

        override fun editMessage(messageId: String, content: String) {
            dispatcher.doBackground(viewModelScope) {
                repository.editMessage(messageId, content)
            }
        }

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
            = connectionWrapper.observeConnection(owner, observer)

        override fun connection() = connectionWrapper.connection(viewModelScope)

        override fun checkNetworkConnection(state: Boolean)
            = connectionWrapper.checkNetworkConnection(state)

    }
}