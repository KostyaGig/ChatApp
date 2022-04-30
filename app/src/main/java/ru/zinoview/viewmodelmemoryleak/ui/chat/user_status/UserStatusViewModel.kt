package ru.zinoview.viewmodelmemoryleak.ui.chat.user_status

import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.data.chat.user_status.UserStatusRepository
import ru.zinoview.viewmodelmemoryleak.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.CommunicationObserve
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher

interface UserStatusViewModel : CommunicationObserve<List<UiMessagesNotification>> {

    fun online()

    fun offline()

    class Base(
        private val repository: UserStatusRepository,
        communication: UiMessagesNotificationCommunication,
        private val dispatcher: Dispatcher
        ) : UserStatusViewModel,BaseViewModel<List<UiMessagesNotification>>(
            repository,communication
    ) {
        override fun online()
            = dispatcher.doBackground(viewModelScope) {
                repository.online()
            }

        override fun offline()
            = dispatcher.doBackground(viewModelScope) {
                repository.offline()
            }

    }
}