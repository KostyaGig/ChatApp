package ru.zinoview.viewmodelmemoryleak.chat.ui.chat

import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.chat.core.Clean
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.chat.ChatRepository
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.ActionViewModel
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Dispatcher

interface ChatViewModel : ChatViewModelObserve,Clean, ActionViewModel<String> {

    fun messages()

    class Base(
        private val repository: ChatRepository,
        private val dispatcher: Dispatcher,
        private val mapper: DataToUiMessageMapper,
        private val communication: MessagesCommunication
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

        override fun observeMessages() {
            repository.observe { messages ->
                val uiMessages = messages.map {
                    it.map(mapper)
                }
                dispatcher.doUi(viewModelScope) {
                    communication.postValue(uiMessages)
                }
            }
        }
    }
}