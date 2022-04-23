package ru.zinoview.viewmodelmemoryleak.data.chat

import androidx.work.Data
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudDataSource

interface ChatAction {

    fun executeWorker(data: List<String>)

    suspend fun sendMessage(data: Data,cloudDataSource: CloudDataSource<Unit>) = Unit
    suspend fun editMessage(data: Data,cloudDataSource: CloudDataSource<Unit>) = Unit

    abstract class Abstract : ChatAction {

        abstract fun keys() : List<String>
        abstract fun doAction(workerData: List<Pair<String,String>>)

        override fun executeWorker(data: List<String>) {
            val workerData = mutableListOf<Pair<String,String>>()
            val keys = keys()

            data.forEachIndexed { index, stringData ->
                val data = Pair(keys[index],stringData)
                workerData.add(data)
            }

            doAction(workerData)
        }
    }

    class SendMessage(
        private val worker: Worker
    ) : Abstract() {

        override suspend fun sendMessage(data: Data, cloudDataSource: CloudDataSource<Unit>) {
            val userId = data.getString(USER_ID_KEY)
            val content = data.getString(MESSAGE_CONTENT_KEY)
            cloudDataSource.sendMessage(userId!!,content!!)
        }

        override fun keys() = listOf(USER_ID_KEY, MESSAGE_CONTENT_KEY)

        override fun doAction(workerData: List<Pair<String, String>>)
            = worker.sendMessage(workerData)

        private companion object {
            private const val USER_ID_KEY = "userId"
            private const val MESSAGE_CONTENT_KEY = "message_content"
        }
    }

    class EditMessage(
        private val worker: Worker
    ) : Abstract() {

        override suspend fun editMessage(data: Data, cloudDataSource: CloudDataSource<Unit>) {
            val messageId = data.getString(MESSAGE_ID_KEY)
            val content = data.getString(MESSAGE_CONTENT_KEY)

            cloudDataSource.editMessage(messageId!!,content!!)
        }

        override fun keys() = listOf(MESSAGE_ID_KEY, MESSAGE_CONTENT_KEY)

        override fun doAction(workerData: List<Pair<String, String>>)
            = worker.editMessage(workerData)

        private companion object {
            private const val MESSAGE_CONTENT_KEY = "message_content"
            private const val MESSAGE_ID_KEY = "messageId"
        }

    }

}