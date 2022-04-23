package ru.zinoview.viewmodelmemoryleak.data.chat

import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.core.chat.EditMessage
import ru.zinoview.viewmodelmemoryleak.core.chat.UpdateMessagesState
import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudMessage
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudToDataMessageMapper
import ru.zinoview.viewmodelmemoryleak.data.core.CleanRepository

interface ChatRepository<T> : Clean, EditMessage,UpdateMessagesState {

    suspend fun sendMessage(content: String)

    suspend fun messages(block: (List<DataMessage>) -> Unit) : T

    class Base(
        private val updateChatCloudDataSource: CloudDataSource.Update,
        private val cloudDataSource: CloudDataSource.Base,
        private val mapper: CloudToDataMessageMapper,
        private val prefs: IdSharedPreferences<Int,Unit>,
        private val sendMessageAction: ChatAction,
        private val editMessageAction: ChatAction
    ) : ChatRepository<Unit>,
        CleanRepository(cloudDataSource) {

        // todo process try catch

        override suspend fun sendMessage(content: String) {
            val userId = prefs.read(Unit).toString()
            val data = listOf(userId,content)

            updateChatCloudDataSource.sendMessage(userId, content)
            sendMessageAction.executeWorker(data)
        }

        override suspend fun editMessage(messageId: String, content: String) {
            updateChatCloudDataSource.editMessage(messageId, content)
            val data = listOf(messageId,content)

            editMessageAction.executeWorker(data)
        }

        override fun updateMessagesState(range: Pair<Int,Int>)
            = cloudDataSource.updateMessagesState(range)

        override suspend fun messages(block: (List<DataMessage>) -> Unit) {
            cloudDataSource.messages { cloud ->
                val data = cloud.map { it.map(mapper) }
                block.invoke(data)
            }
        }
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
            cloudDataSource.sendMessage(userId.toString(),content)
            count++
        }

        override fun updateMessagesState(range: Pair<Int, Int>)
            = cloudDataSource.updateMessagesState(range)

        override suspend fun messages(block: (List<DataMessage>) -> Unit)
            = cloudDataSource.messages {}.map { it.map(mapper) }

    }
}