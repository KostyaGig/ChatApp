package ru.zinoview.viewmodelmemoryleak.domain.chat

import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.core.chat.*
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatRepository
import ru.zinoview.viewmodelmemoryleak.data.chat.processing_messages.ProcessingChatMessagesRepository
import ru.zinoview.viewmodelmemoryleak.data.chat.update.ImmediatelyUpdateChatRepository
import ru.zinoview.viewmodelmemoryleak.ui.chat.ReadMessages

interface ChatInteractor : Messages<DomainMessage>, SendMessage,
    EditMessage, ShowProcessingMessages, ReadMessages , ToTypeMessage.Unit, Clean {

    class Base(
        private val chatRepository: ChatRepository<Unit>,
        private val updateChatRepository: ImmediatelyUpdateChatRepository,
        private val processingMessagesRepository: ProcessingChatMessagesRepository,
        private val mapper: DataToDomainMessageMapper
    ) : ChatInteractor {

        override suspend fun sendMessage(content: String) {
            processingMessagesRepository.sendMessage(content)
            updateChatRepository.sendMessage(content)
            chatRepository.sendMessage(content)
        }

        override suspend fun editMessage(messageId: String, content: String) {
            processingMessagesRepository.editMessage(messageId,content)
            updateChatRepository.editMessage(messageId,content)
            chatRepository.editMessage(messageId, content)
        }

        override suspend fun toTypeMessage(isTyping: Boolean)
            = chatRepository.toTypeMessage(isTyping)

        override fun showProcessingMessages()
            = processingMessagesRepository.show(Unit)

        override fun readMessages(range: Pair<Int, Int>) = chatRepository.readMessages(range)


        override suspend fun messages(block: (List<DomainMessage>) -> Unit) {
            chatRepository.messages { dataMessages ->
                val domainMessages = dataMessages.map { it.map(mapper)}
                block.invoke(domainMessages)
            }
        }

        override fun clean() = chatRepository.clean()
    }
}