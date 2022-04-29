package ru.zinoview.viewmodelmemoryleak.ui.chat.user_status

import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.core.chat.user_status.Offline
import ru.zinoview.viewmodelmemoryleak.core.chat.user_status.Online
import ru.zinoview.viewmodelmemoryleak.data.chat.notification.NotificationMessageRepository
import ru.zinoview.viewmodelmemoryleak.data.chat.user_status.UserStatusRepository
import ru.zinoview.viewmodelmemoryleak.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.CommunicationObserve
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher

interface UserStatusViewModel : CommunicationObserve<List<UiMessagesNotification>> {

    fun online()

    fun offline()

    // todo clean not used classes
    class Base(
        private val userStatusRepository: UserStatusRepository,
        repository: NotificationMessageRepository,
        communication: UiMessagesNotificationCommunication,
        private val dispatcher: Dispatcher
        ) : UserStatusViewModel,BaseViewModel<List<UiMessagesNotification>>(
        repository,communication
    ) {
        override fun online()
            = dispatcher.doBackground(viewModelScope) {
                userStatusRepository.online()
            }

        override fun offline()
            = dispatcher.doBackground(viewModelScope) {
                userStatusRepository.offline()
            }

    }
}