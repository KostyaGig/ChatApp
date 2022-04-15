package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatRepository
import ru.zinoview.viewmodelmemoryleak.ui.connection.UiConnection
import ru.zinoview.viewmodelmemoryleak.ui.connection.UiConnectionWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.ActionViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher

interface ChatViewModel : ChatViewModelObserve, Clean,
    ActionViewModel<String>, ru.zinoview.viewmodelmemoryleak.ui.core.Connection {

    fun messages()

    fun editMessage(messageId: String, content: String)

    fun test()

    // todo use Work instead of scope switchers
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
                delay(5000)
                repository.messages { data ->
                    val ui = data.map { it.map(mapper) }
                    dispatcher.doUi(viewModelScope) {
                        communication.postValue(ui)
                    }
                }
            }
        }

        override fun test() {
            Log.d("zinoviewk","chat viewmodel test")
        }

        override fun observeConnection(owner: LifecycleOwner,observer: Observer<UiConnection>)
            = connectionWrapper.observeConnection(owner, observer)

        override fun connection() = connectionWrapper.connection(viewModelScope)

        override fun checkNetworkConnection(state: Boolean)
            = connectionWrapper.checkNetworkConnection(state)
    }
}