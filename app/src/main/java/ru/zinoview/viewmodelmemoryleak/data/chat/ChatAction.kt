package ru.zinoview.viewmodelmemoryleak.data.chat

import android.util.Log
import androidx.work.Data
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudDataSource

interface ChatAction {

    fun executeWorker(worker: Worker, data: List<String>)

    suspend fun sendMessage(data: Data,cloudDataSource: CloudDataSource<Unit>) = Unit
    suspend fun editMessage(data: Data,cloudDataSource: CloudDataSource<Unit>) = Unit


    class SendMessage : ChatAction {

        override suspend fun sendMessage(data: Data, cloudDataSource: CloudDataSource<Unit>) {
            val userId = data.getString(USER_ID_KEY)
            val content = data.getString(MESSAGE_CONTENT_KEY)
            cloudDataSource.sendMessage(userId!!,content!!)
        }

        override fun executeWorker(worker: Worker, data: List<String>) {
            val workerData = mutableListOf<Pair<String,String>>()
            val keys = listOf(USER_ID_KEY, MESSAGE_CONTENT_KEY)

            data.forEachIndexed { index, stringData ->
                val data = Pair(keys[index],stringData)
                workerData.add(data)
            }

            worker.sendMessage(workerData)
        }

        private companion object {
            private const val USER_ID_KEY = "userId"
            private const val MESSAGE_CONTENT_KEY = "message_content"
        }
    }

    class EditMessage : ChatAction {

        override fun executeWorker(worker: Worker, data: List<String>) {
            Log.d("zinoviewk","edit message action $data")
            val workerData = mutableListOf<Pair<String,String>>()
            val keys = listOf(MESSAGE_ID_KEY, MESSAGE_CONTENT_KEY)

            data.forEachIndexed { index, stringData ->
                val data = Pair(keys[index],stringData)
                workerData.add(data)
            }

            worker.editMessage(workerData)
        }

        override suspend fun editMessage(data: Data, cloudDataSource: CloudDataSource<Unit>) {
            val messageId = data.getString(MESSAGE_ID_KEY)
            val content = data.getString(MESSAGE_CONTENT_KEY)

            cloudDataSource.editMessage(messageId!!,content!!)
        }

        private companion object {
            private const val MESSAGE_CONTENT_KEY = "message_content"
            private const val MESSAGE_ID_KEY = "messageId"
        }

    }

}