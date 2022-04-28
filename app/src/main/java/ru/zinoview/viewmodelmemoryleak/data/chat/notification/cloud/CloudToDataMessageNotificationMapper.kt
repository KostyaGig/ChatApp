package ru.zinoview.viewmodelmemoryleak.data.chat.notification.cloud

import ru.zinoview.viewmodelmemoryleak.core.chat.notification.MessageNotification
import ru.zinoview.viewmodelmemoryleak.data.chat.notification.DataMessageNotification

interface CloudToDataMessageNotificationMapper : MessageNotification.Mapper<DataMessageNotification> {

    class Base : CloudToDataMessageNotificationMapper {

        override fun map(time: String, content: String)
            = DataMessageNotification.Base(time, content)
    }
}