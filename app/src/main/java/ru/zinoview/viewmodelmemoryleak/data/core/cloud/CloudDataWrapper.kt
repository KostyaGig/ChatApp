package ru.zinoview.viewmodelmemoryleak.data.core.cloud

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper

abstract class CloudDataWrapper<E,T : Mapper<E>>(
    private val values: List<T>,
    empty: E
) : Mapper.Base<List<E>>(listOf(empty)) {

    override fun map(id: String, senderId: String, content: String, senderNickname: String) =
        values.map { it.map(id, senderId, content, senderNickname) }
}