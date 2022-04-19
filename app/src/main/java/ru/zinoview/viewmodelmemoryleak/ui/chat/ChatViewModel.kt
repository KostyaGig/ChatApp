package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.core.chat.UpdateMessagesState
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatRepository
import ru.zinoview.viewmodelmemoryleak.ui.connection.UiConnection
import ru.zinoview.viewmodelmemoryleak.ui.connection.UiConnectionWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.ActionViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher

interface ChatViewModel : ChatViewModelObserve, Clean,
    ActionViewModel<String>,
    ru.zinoview.viewmodelmemoryleak.ui.core.ConnectionViewModel,
    UpdateMessagesState {

    fun messages()

    fun editMessage(messageId: String, content: String)

    // todo move to inteface
    fun observeScrollCommunication(owner: LifecycleOwner,observer: Observer<UiScroll>)

    class Base(
        private val repository: ChatRepository<Unit>,
        private val work: ChatWork,
        private val dispatcher: Dispatcher,
        private val mapper: DataToUiMessageMapper,
        private val communication: MessagesCommunication,
        private val scrollCommunication: ScrollCommunication,
        private val connectionWrapper: UiConnectionWrapper
    ) : BaseViewModel<List<UiChatMessage>>(repository,communication), ChatViewModel {

        override fun doAction(content: String)
            = work.doBackground(viewModelScope) {
                repository.sendMessage(content)
            }

        override fun editMessage(messageId: String, content: String)
            = work.doBackground(viewModelScope) {
                repository.editMessage(messageId, content)
            }

        override fun messages() {
            Log.d("zinoviewk","MESSAGES METHOD CALL")
            communication.postValue(listOf(UiChatMessage.Progress))
            work.doBackground(viewModelScope) {
                repository.messages { data ->
                    val ui = data.map { it.map(mapper) }

                    dispatcher.doUi(viewModelScope) {
                        val first = ui.first()
                        Log.d("zinoviewk","FIRST $first")
                        first.addScroll(scrollCommunication)
                        communication.postValue(ui)
                    }
                }
            }
        }

        override fun updateMessagesState(
            range: Pair<Int,Int>
        ) = repository.updateMessagesState(range)

        override fun observeConnection(owner: LifecycleOwner,observer: Observer<UiConnection>)
            = connectionWrapper.observeConnection(owner, observer)

        override fun connection() = connectionWrapper.connection(viewModelScope)

        override fun updateNetworkState(isConnected: Boolean,arg: Unit)
            = work.doBackground(viewModelScope) {
                connectionWrapper.updateNetworkState(isConnected,viewModelScope)
            }

        override fun observeScrollCommunication(
            owner: LifecycleOwner,
            observer: Observer<UiScroll>
        ) = scrollCommunication.observe(owner, observer)
    }
}