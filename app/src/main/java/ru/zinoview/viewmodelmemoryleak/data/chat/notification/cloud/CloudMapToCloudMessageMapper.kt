package ru.zinoview.viewmodelmemoryleak.data.chat.notification.cloud

import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.data.core.EmptyString

interface CloudMapToCloudMessageMapper : Mapper<MutableMap<String?,String?>,CloudMessage> {

    data class Base(
        private val emptyString: EmptyString
    ) : CloudMapToCloudMessageMapper {

        override fun map(map: MutableMap<String?, String?>): CloudMessage {
            val id = emptyString.string(map[MESSAGE_ID_KEY])
            val nickName = emptyString.string(map[NICKNAME_KEY])
            val content = emptyString.string(map[CONTENT_KEY])

            return CloudMessage.Base(id,nickName,content)
        }

        private companion object {
            private const val NICKNAME_KEY = "nickName"
            private const val MESSAGE_ID_KEY = "messageId"
            private const val CONTENT_KEY = "content"
        }
    }
}