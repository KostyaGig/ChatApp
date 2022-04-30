package ru.zinoview.viewmodelmemoryleak.data.chat.update.cloud

import ru.zinoview.viewmodelmemoryleak.core.Time
import ru.zinoview.viewmodelmemoryleak.core.chat.EditMessage
import ru.zinoview.viewmodelmemoryleak.core.chat.SendMessageWithSenderData
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudMessage
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.MessagesStore

interface CloudDataSource : SendMessageWithSenderData, EditMessage {


    class Base(
        private val messagesStore: MessagesStore,
        private val time: Time<String>
    ) : CloudDataSource {

        override suspend fun sendMessage(userId: String, nickName: String, content: String) {
            val progressMessage = CloudMessage.Progress.Send(
                userId,
                content,
                time.time()
            )

            messagesStore.add(progressMessage)
        }

        override suspend fun editMessage(messageId: String, content: String)
            = messagesStore.editMessage(messageId, content)
    }
}