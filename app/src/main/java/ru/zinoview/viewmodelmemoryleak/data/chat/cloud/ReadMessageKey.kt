package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

interface ReadMessageKey {

    fun pair(senderId: String,receiverId: String,ids: CloudUnreadMessageIds) : Pair<String,Any>

    abstract class Base(
        private val key: String
    ) : ReadMessageKey {

        abstract fun data(senderId: String,receiverId: String,ids: CloudUnreadMessageIds) : Any

        override fun pair(
            senderId: String,
            receiverId: String,
            ids: CloudUnreadMessageIds
        ) = Pair(key,data(senderId, receiverId, ids))

    }

    class Ids : Base(KEY) {

        override fun data(
            senderId: String,
            receiverId: String,
            ids: CloudUnreadMessageIds
        ) = ids

        private companion object {
            private const val KEY = "ids"
        }
    }

    class SenderId : Base(KEY) {
        override fun data(
            senderId: String,
            receiverId: String,
            ids: CloudUnreadMessageIds
        ) = senderId

        private companion object {
            private const val KEY = "senderId"
        }
    }

    class ReceiverId : Base(KEY) {
        override fun data(
            senderId: String,
            receiverId: String,
            ids: CloudUnreadMessageIds
        ) = senderId

        private companion object {
            private const val KEY = "receiverId"
        }
    }

}