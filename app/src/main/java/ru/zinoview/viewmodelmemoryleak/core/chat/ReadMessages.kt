package ru.zinoview.viewmodelmemoryleak.core.chat

import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketData
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.*
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Json

interface ReadMessages : UnreadMessageIds<MessagesStore> {

    fun json(json: Json,store: MessagesStore,keys: List<ReadMessageKey>) : SocketData = SocketData.Empty
    override fun unreadMessageIds(src: MessagesStore): List<String> = emptyList()
    fun dataReadMessages(senderId: String) : ReadMessages = Empty

    class Base(
        private val range: Pair<Int,Int>,
        private val receiverId: String
    ) : ReadMessages {

        override fun dataReadMessages(senderId: String) = Data(range, receiverId, senderId)
    }

    class Data(
        private val range: Pair<Int,Int>,
        private val receiverId: String,
        private val senderId: String
    ) : ReadMessages {
        override fun json(json: Json,store: MessagesStore,keys: List<ReadMessageKey>): SocketData {
            val ids =  CloudUnreadMessageIds.Base(store.unreadMessageIds(range))

            val pairs = keys.map { key ->
                key.pair(senderId,receiverId,ids)
            }

            return SocketData.Base(
                json.json(pairs)
            )
        }

        override fun unreadMessageIds(store: MessagesStore) = store.unreadMessageIds(range)
    }

    object Empty : ReadMessages
}