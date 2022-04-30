package ru.zinoview.viewmodelmemoryleak.data.chat

import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.core.chat.EditMessage
import ru.zinoview.viewmodelmemoryleak.core.chat.Messages
import ru.zinoview.viewmodelmemoryleak.core.chat.ShowProcessingMessages
import ru.zinoview.viewmodelmemoryleak.data.cache.UserSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudToDataMessageMapper
import ru.zinoview.viewmodelmemoryleak.core.chat.SendMessage
import ru.zinoview.viewmodelmemoryleak.data.core.CleanRepository
import ru.zinoview.viewmodelmemoryleak.ui.chat.ReadMessages

interface ChatRepository<T> : Messages<DataMessage>, SendMessage, EditMessage ,ReadMessages, ShowProcessingMessages,Clean {

    override fun showProcessingMessages() = Unit

    override suspend fun messages(block: (List<DataMessage>) -> Unit) = Unit
    class Base(
        private val cloudDataSource: CloudDataSource<Unit>,
        private val mapper: CloudToDataMessageMapper,
        private val userSharedPreferences: UserSharedPreferences,
        private val chatActions: ChatActions
    ) : ChatRepository<Unit>,
        CleanRepository(cloudDataSource) {

        override suspend fun sendMessage(content: String) {
            val userId = userSharedPreferences.id().toString()
            val userNickName = userSharedPreferences.nickName()

            val data = listOf(userId,userNickName,content)

            chatActions.sendMessage(data)
        }

        override suspend fun editMessage(messageId: String, content: String) {
            val data = listOf(messageId,content)

            chatActions.editMessage(data)
        }

        override fun readMessages(range: Pair<Int,Int>)
            = cloudDataSource.readMessages(range)

        override suspend fun messages(block: (List<DataMessage>) -> Unit) {
            cloudDataSource.messages { cloud ->
                val data = cloud.map { it.map(mapper) }
                block.invoke(data)
            }
        }

        override fun showProcessingMessages() = Unit
    }

    class Test(
        private val cloudDataSource: CloudDataSource.Test,
        private val mapper: CloudToDataMessageMapper
    ) : ChatRepository<DataMessage> {
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

        fun messages() : List<DataMessage> = cloudDataSource.messages().map { it.map(mapper) }

    }
}