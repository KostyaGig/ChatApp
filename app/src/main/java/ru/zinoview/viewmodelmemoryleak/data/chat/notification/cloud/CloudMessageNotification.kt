package ru.zinoview.viewmodelmemoryleak.data.chat.notification.cloud

import ru.zinoview.viewmodelmemoryleak.core.chat.notification.MessageNotification

interface CloudMessageNotification : MessageNotification {

    class Base(
        private val time: String,
        private val content: String
    ): CloudMessageNotification {

        override fun <T> map(mapper: MessageNotification.Mapper<T>)
            = mapper.map(time,content)

    }
}