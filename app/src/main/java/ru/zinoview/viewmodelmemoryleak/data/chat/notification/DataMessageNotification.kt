package ru.zinoview.viewmodelmemoryleak.data.chat.notification

import ru.zinoview.viewmodelmemoryleak.core.chat.notification.MessageNotification

interface DataMessageNotification : MessageNotification {

    class Base(
        private val time: String,
        private val content: String,
    ) : DataMessageNotification {

        override fun <T> map(mapper: MessageNotification.Mapper<T>): T
            = mapper.map(time, content)
    }
}