package ru.zinoview.viewmodelmemoryleak.ui.chat

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.core.chat.ShowProcessingMessages
import ru.zinoview.viewmodelmemoryleak.domain.chat.ChatInteractor
import ru.zinoview.viewmodelmemoryleak.ui.connection.UiConnection
import ru.zinoview.viewmodelmemoryleak.ui.connection.UiConnectionWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.ActionViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher

interface ChatViewModel : ChatViewModelObserve, Clean,
    ActionViewModel<String>,
    ru.zinoview.viewmodelmemoryleak.ui.core.ConnectionViewModel,
    ReadMessages,
    ObserveScroll,
    ShowProcessingMessages {

    fun messages()

    fun editMessage(messageId: String, content: String)

    fun updateTypeMessageState(isTyping: Boolean)

    class Base(
        private val interactor: ChatInteractor,
        private val work: ChatWork,
        private val dispatcher: Dispatcher,
        private val mapper: DomainToUiMessageMapper,
        private val communication: MessagesCommunication,
        private val scroll: Scroll,
        // todo move to another viewModel
        private val connectionWrapper: UiConnectionWrapper
    ) : BaseViewModel<List<UiMessage>>(listOf(
            interactor,scroll
        ),communication), ChatViewModel {

        override fun doAction(content: String)
            = work.doBackground(viewModelScope) {
                interactor.sendMessage(content)
            }

        override fun editMessage(messageId: String, content: String)
            = work.doBackground(viewModelScope) {
                interactor.editMessage(messageId, content)
            }

        override fun messages() {
            communication.postValue(listOf(UiMessage.Progress))
            work.doBackground(viewModelScope) {
                interactor.messages { domain ->
                    val uiMessages = domain.map { it.map(mapper) }

                    dispatcher.doUi(viewModelScope) {
                        communication.postValue(uiMessages)
                        scroll.add(uiMessages)
                    }
                }
            }
        }

        override fun updateTypeMessageState(isTyping: Boolean)
            = work.doBackground(viewModelScope) {
                interactor.toTypeMessage(isTyping)
            }


        override fun readMessages(range: Pair<Int, Int>) = interactor.readMessages(range)

        override fun showProcessingMessages() = interactor.showProcessingMessages()

        override fun observeScrollCommunication(
            owner: LifecycleOwner,
            observer: Observer<UiScroll>
        ) = scroll.observeScrollCommunication(owner, observer)

        override fun observeConnection(owner: LifecycleOwner,observer: Observer<UiConnection>)
            = connectionWrapper.observeConnection(owner, observer)

        override fun connection() = connectionWrapper.connection(viewModelScope)

        override fun updateNetworkState(isConnected: Boolean,arg: Unit)
            = work.doBackground(viewModelScope) {
                connectionWrapper.updateNetworkState(isConnected,viewModelScope)
            }

    }
}