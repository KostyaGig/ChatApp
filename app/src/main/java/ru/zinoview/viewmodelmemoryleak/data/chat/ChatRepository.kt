package ru.zinoview.viewmodelmemoryleak.data.chat

import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.core.chat.*
import ru.zinoview.viewmodelmemoryleak.core.chat.SendMessage
import ru.zinoview.viewmodelmemoryleak.data.cache.UserSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudToDataMessageMapper
import ru.zinoview.viewmodelmemoryleak.data.core.CleanRepository
import ru.zinoview.viewmodelmemoryleak.ui.chat.ReadMessages

interface ChatRepository<T> : Messages<DataMessage>, SendMessage, EditMessage ,
    ReadMessages, ToTypeMessage<T>, ShowProcessingMessages,Clean {

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
            val userId = userSharedPreferences.id()
            val userNickName = userSharedPreferences.nickName()

            val data = listOf(userId,userNickName,content)

            chatActions.sendMessage(data)
        }

        override suspend fun editMessage(messageId: String, content: String) {
            val data = listOf(messageId,content)

            chatActions.editMessage(data)
        }

        override suspend fun toTypeMessage(isTyping: Boolean)  {
            val userNickName = userSharedPreferences.nickName()
            cloudDataSource.toTypeMessage(isTyping,userNickName)
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

}