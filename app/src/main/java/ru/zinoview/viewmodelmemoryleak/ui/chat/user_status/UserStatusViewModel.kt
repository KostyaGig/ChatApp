package ru.zinoview.viewmodelmemoryleak.ui.chat.user_status

import ru.zinoview.viewmodelmemoryleak.core.chat.user_status.Offline
import ru.zinoview.viewmodelmemoryleak.core.chat.user_status.Online
import ru.zinoview.viewmodelmemoryleak.data.chat.notification.NotificationMessageRepository
import ru.zinoview.viewmodelmemoryleak.data.chat.user_status.UserStatusRepository
import ru.zinoview.viewmodelmemoryleak.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.CommunicationObserve

interface UserStatusViewModel : CommunicationObserve<List<UiMessagesNotification>>,Online,Offline {

    class Base(
        private val userStatusRepository: UserStatusRepository,
        private val repository: NotificationMessageRepository,
        communication: UiMessagesNotificationCommunication
    ) : UserStatusViewModel,BaseViewModel<List<UiMessagesNotification>>(
        repository,communication
    ) {
        override fun online() = userStatusRepository.online()

        override fun offline() = userStatusRepository.offline()
    }
}