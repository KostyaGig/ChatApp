package ru.zinoview.viewmodelmemoryleak.domain.chat

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.core.chat.*
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatRepository
import ru.zinoview.viewmodelmemoryleak.data.chat.processing_messages.ProcessingChatMessagesRepository
import ru.zinoview.viewmodelmemoryleak.data.chat.update.ImmediatelyUpdateChatRepository
import ru.zinoview.viewmodelmemoryleak.core.chat.ReadMessage

interface ChatInteractor : Messages<DomainMessage>, SendMessage,
    EditMessage, ShowProcessingMessages, ReadMessage, ToTypeMessage.Unit,ShowNotificationMessage,
    Clean {

    class Base(
        private val chatRepository: ChatRepository<Unit>,
        private val updateChatRepository: ImmediatelyUpdateChatRepository,
        private val processingMessagesRepository: ProcessingChatMessagesRepository,
        private val mapper: DataToDomainMessageMapper
    ) : ChatInteractor {

        override suspend fun sendMessage(receiverId: String,content: String) {
            processingMessagesRepository.sendMessage(receiverId,content)
            updateChatRepository.sendMessage(receiverId,content)
            chatRepository.sendMessage(receiverId,content)
        }

        override suspend fun editMessage(messageId: String, content: String,receiverId: String) {
            processingMessagesRepository.editMessage(messageId,content,receiverId)
            updateChatRepository.editMessage(messageId,content,receiverId)
            chatRepository.editMessage(messageId, content,receiverId)
        }

        override suspend fun toTypeMessage(isTyping: Boolean)
            = chatRepository.toTypeMessage(isTyping)

        override suspend fun showNotificationMessage(messageId: String)
            = chatRepository.showNotificationMessage(messageId)

        override fun showProcessingMessages()
            = processingMessagesRepository.show(Unit)

        override fun readMessages(readMessages: ReadMessages)
            = chatRepository.readMessages(readMessages)

        override suspend fun messages(receiverId: String,block: (List<DomainMessage>) -> Unit) {
            chatRepository.messages(receiverId) { dataMessages ->
                val domainMessages = dataMessages.map { it.map(mapper)}

                block.invoke(domainMessages)
            }
        }

        override fun clean() = chatRepository.clean()
    }
}