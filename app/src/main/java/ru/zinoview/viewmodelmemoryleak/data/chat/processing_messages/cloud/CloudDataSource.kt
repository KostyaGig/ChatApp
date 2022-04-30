package ru.zinoview.viewmodelmemoryleak.data.chat.processing_messages.cloud

import ru.zinoview.viewmodelmemoryleak.core.Time
import ru.zinoview.viewmodelmemoryleak.core.chat.EditMessage
import ru.zinoview.viewmodelmemoryleak.core.chat.SendMessageWithSenderData
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.*
import ru.zinoview.viewmodelmemoryleak.ui.core.Show

interface CloudDataSource : SendMessageWithSenderData, EditMessage, Show<Unit> {

    class Base(
        private val processingMessages: ProcessingMessages,
        private val messagesStore: MessagesStore,
        private val time: Time<String>
    ) : CloudDataSource {

        override suspend fun sendMessage(userId: String, nickName: String, content: String) {
            val progressMessage = CloudMessage.Progress.Send(
                userId,
                content,
                time.time()
            )

            processingMessages.add(progressMessage)
        }

        override suspend fun editMessage(messageId: String, content: String)
            = messagesStore.updateProcessingMessages(processingMessages,messageId, content)

        override fun show(arg: Unit) = processingMessages.show(arg)
    }
}