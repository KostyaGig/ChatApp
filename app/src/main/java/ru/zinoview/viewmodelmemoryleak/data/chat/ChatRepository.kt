package ru.zinoview.viewmodelmemoryleak.data.chat

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.core.chat.EditMessage
import ru.zinoview.viewmodelmemoryleak.core.chat.ShowProcessingMessages
import ru.zinoview.viewmodelmemoryleak.data.cache.UserSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudMessage
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudToDataMessageMapper
import ru.zinoview.viewmodelmemoryleak.data.chat.workmanager.Worker
import ru.zinoview.viewmodelmemoryleak.data.core.CleanRepository
import ru.zinoview.viewmodelmemoryleak.ui.chat.ReadMessages

interface ChatRepository<T> : Clean, EditMessage,ReadMessages,ShowProcessingMessages {

    suspend fun sendMessage(content: String)

    suspend fun messages(block: (List<DataMessage>) -> Unit) : T

    override fun showProcessingMessages() = Unit

    class Base(
        // todo move
        private val updateChatCloudDataSource: CloudDataSource.Update,
        private val cloudDataSource: CloudDataSource.Base,
        private val mapper: CloudToDataMessageMapper,
        private val userSharedPreferences: UserSharedPreferences,
        private val sendMessageAction: ChatAction,
        private val editMessageAction: ChatAction,
        private val worker: Worker
    ) : ChatRepository<Unit>,
        CleanRepository(cloudDataSource) {

        override suspend fun sendMessage(content: String) {
            val userId = userSharedPreferences.id().toString()
            val userNickName = userSharedPreferences.nickName()
            val data = listOf(userId,userNickName,content)

            updateChatCloudDataSource.sendMessage(userId,userNickName,content)
            sendMessageAction.executeWorker(worker,data)
        }

        override suspend fun editMessage(messageId: String, content: String) {
            updateChatCloudDataSource.editMessage(messageId, content)
            val data = listOf(messageId,content)

            editMessageAction.executeWorker(worker,data)
        }

        override fun readMessages(range: Pair<Int,Int>)
            = cloudDataSource.readMessages(range)

        override suspend fun messages(block: (List<DataMessage>) -> Unit) {
            cloudDataSource.messages { cloud ->
                val data = cloud.map { it.map(mapper) }
                block.invoke(data)
            }
        }

        override fun showProcessingMessages()
            = updateChatCloudDataSource.showProcessingMessages()
    }

    class Test(
        private val cloudDataSource: CloudDataSource<List<CloudMessage>>,
        private val mapper: CloudToDataMessageMapper
    ) : ChatRepository<List<DataMessage>> {
        override fun clean() = Unit

        private var count = 0

        override suspend fun editMessage(messageId: String, content: String)
            = cloudDataSource.editMessage(messageId, content)

        override suspend fun sendMessage(content: String) {
            val userId = if (count % 2 == 0 ) 1 else 2
            cloudDataSource.sendMessage(userId.toString(),"fake nick name",content)
            count++
        }

        override fun readMessages(range: Pair<Int, Int>)
            = cloudDataSource.readMessages(range)

        override suspend fun messages(block: (List<DataMessage>) -> Unit)
            = cloudDataSource.messages {}.map { it.map(mapper) }

    }
}