package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.core.chat.ReadMessage
import ru.zinoview.viewmodelmemoryleak.core.chat.ReadMessages
import ru.zinoview.viewmodelmemoryleak.core.chat.ShowProcessingMessages
import ru.zinoview.viewmodelmemoryleak.core.chat.Subscribe
import ru.zinoview.viewmodelmemoryleak.domain.chat.ChatInteractor
import ru.zinoview.viewmodelmemoryleak.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher

interface ChatViewModel : ChatViewModelObserve, Clean,
    ReadMessage,
    ObserveScroll,
    ShowProcessingMessages,Subscribe {

    fun messages(receiverId: String)

    fun sendMessage(receiverId: String, content: String)

    fun editMessage(messageId: String, content: String, receiverId: String)

    fun toTypeMessage(isTyping: Boolean)

    fun showNotificationMessage(messageId: String)

    class Base(
        private val interactor: ChatInteractor,
        private val work: ChatWork,
        private val dispatcher: Dispatcher,
        private val mapper: DomainToUiMessageMapper,
        private val communication: MessagesCommunication,
        private val scroll: Scroll,
    ) : BaseViewModel<List<UiMessage>>(
        communication, listOf(communication,interactor, scroll)
    ), ChatViewModel {

        override fun sendMessage(receiverId: String, content: String) =
            work.doBackground(viewModelScope) {
                interactor.sendMessage(receiverId, content)
            }

        override fun editMessage(messageId: String, content: String, receiverId: String) =
            work.doBackground(viewModelScope) {
                interactor.editMessage(messageId, content, receiverId)
            }

        override fun subscribeToChanges() = interactor.subscribeToChanges()

        override fun messages(receiverId: String) {
            communication.postValue(listOf(UiMessage.Empty))
            work.doBackground(viewModelScope) {
                interactor.messages(receiverId) { domain ->
                    val uiMessages = domain.map { it.map(mapper) }

                    dispatcher.doUi(viewModelScope) {
                        communication.postValue(uiMessages)
                        scroll.add(uiMessages)
                    }
                }
            }
        }

        override fun toTypeMessage(isTyping: Boolean) = work.doBackground(viewModelScope) {
            interactor.toTypeMessage(isTyping)
        }

        override fun showNotificationMessage(messageId: String) =
            work.doBackground(viewModelScope) {
                interactor.showNotificationMessage(messageId)
            }

        override fun readMessages(readMessages: ReadMessages) =
            interactor.readMessages(readMessages)

        override fun showProcessingMessages() = interactor.showProcessingMessages()

        override fun observeScrollCommunication(
            owner: LifecycleOwner,
            observer: Observer<UiScroll>
        ) = scroll.observeScrollCommunication(owner, observer)

    }
}