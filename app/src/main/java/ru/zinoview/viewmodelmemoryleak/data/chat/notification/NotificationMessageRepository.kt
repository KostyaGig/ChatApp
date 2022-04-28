package ru.zinoview.viewmodelmemoryleak.data.chat.notification

import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.core.chat.user_status.Offline
import ru.zinoview.viewmodelmemoryleak.core.chat.user_status.Online
import ru.zinoview.viewmodelmemoryleak.data.chat.notification.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.chat.notification.cloud.CloudToDataMessageNotificationMapper
import ru.zinoview.viewmodelmemoryleak.data.chat.user_status.UserStatus
import ru.zinoview.viewmodelmemoryleak.data.chat.user_status.UserStatusWrapper
import ru.zinoview.viewmodelmemoryleak.data.core.CleanRepository
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Subscribe

interface NotificationMessageRepository : Subscribe<List<DataMessageNotification>>, Clean {

    class Base(
        private val cloudDataSource: CloudDataSource,
        private val mapper: CloudToDataMessageNotificationMapper
    ) : NotificationMessageRepository, CleanRepository(cloudDataSource) {

        override fun subscribe(block: (List<DataMessageNotification>) -> Unit) {
            cloudDataSource.subscribe { cloudNotifications ->
                val data = cloudNotifications.map { cloud -> cloud.map(mapper) }
                block.invoke(data)
            }
        }
    }


    // todo test
    class Test : NotificationMessageRepository {
        override fun subscribe(block: (List<DataMessageNotification>) -> Unit) {
        }

        override fun clean() {

        }


    }
}