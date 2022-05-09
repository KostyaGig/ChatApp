package ru.zinoview.viewmodelmemoryleak.data.core.cloud

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper

abstract class AbstractValue<T>(
    private val nameValuePairs: T,
    empty: T
) : Mapper.Base<T>(empty) {

    override fun map(id: String, senderId: Int, content: String, senderNickname: String): T
            = nameValuePairs
}