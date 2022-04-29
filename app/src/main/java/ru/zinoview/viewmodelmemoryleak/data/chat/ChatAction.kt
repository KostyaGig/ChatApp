package ru.zinoview.viewmodelmemoryleak.data.chat

import android.util.Log
import androidx.work.Data
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.chat.workmanager.Worker
import ru.zinoview.viewmodelmemoryleak.ui.chat.notification.ShowNotification
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect

interface ChatAction {

    fun executeWorker(worker: Worker, data: List<String>)

    suspend fun sendMessage(data: Data,cloudDataSource: CloudDataSource<Unit>) = Unit
    suspend fun editMessage(data: Data,cloudDataSource: CloudDataSource<Unit>) = Unit

    abstract class Abstract(
        private val notification: ShowNotification
    ) : ChatAction, Disconnect<String> {

        abstract fun keys() : List<String>
        abstract fun doAction(worker: Worker, workerData: List<Pair<String,String>>)


        override fun executeWorker(worker: Worker, data: List<String>) {
            val workerData = mutableListOf<Pair<String,String>>()
            val keys = keys()

            data.forEachIndexed { index, stringData ->
                val data = Pair(keys[index],stringData)
                workerData.add(data)
            }

            doAction(worker,workerData)
        }

        override fun disconnect(content: String)
            = notification.disconnect(content)
    }

    class SendMessage(
        notification: ShowNotification
    ) : Abstract(notification) {

        override suspend fun sendMessage(data: Data, cloudDataSource: CloudDataSource<Unit>) {
            val userId = data.getString(USER_ID_KEY)
            val userNickName = data.getString(USER_NICK_NAME_KEY)
            val content = data.getString(MESSAGE_CONTENT_KEY)
            cloudDataSource.sendMessage(userId!!,userNickName!!,content!!)

            disconnect(content)
        }

        override fun keys() = listOf(USER_ID_KEY,USER_NICK_NAME_KEY ,MESSAGE_CONTENT_KEY)

        override fun doAction(worker: Worker, workerData: List<Pair<String, String>>)
            = worker.sendMessage(workerData)

        private companion object {
            private const val USER_ID_KEY = "userId"
            private const val USER_NICK_NAME_KEY = "userNickName"
            private const val MESSAGE_CONTENT_KEY = "message_content"
        }
    }

    class EditMessage(
        notification: ShowNotification
    ): Abstract(notification) {

        override suspend fun editMessage(data: Data, cloudDataSource: CloudDataSource<Unit>) {
            val messageId = data.getString(MESSAGE_ID_KEY)
            val content = data.getString(MESSAGE_CONTENT_KEY)
            cloudDataSource.editMessage(messageId!!,content!!)

            disconnect(content)
        }

        override fun keys() = listOf(MESSAGE_ID_KEY, MESSAGE_CONTENT_KEY)

        override fun doAction(worker: Worker, workerData: List<Pair<String, String>>)
            = worker.editMessage(workerData)

        private companion object {
            private const val MESSAGE_CONTENT_KEY = "message_content"
            private const val MESSAGE_ID_KEY = "messageId"
        }

    }

}