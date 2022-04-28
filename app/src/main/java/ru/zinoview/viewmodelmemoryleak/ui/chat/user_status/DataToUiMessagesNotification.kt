package ru.zinoview.viewmodelmemoryleak.ui.chat.user_status

import ru.zinoview.viewmodelmemoryleak.core.chat.notification.MessageNotification

interface DataToUiMessagesNotification : MessageNotification.Mapper<UiMessagesNotification> {

    class Base : DataToUiMessagesNotification {

        override fun map(time: String, content: String)
            = UiMessagesNotification.Base(time, content)
    }
}