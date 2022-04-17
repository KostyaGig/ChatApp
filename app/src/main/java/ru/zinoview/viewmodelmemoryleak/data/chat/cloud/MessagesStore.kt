package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.chat.EditMessage
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Subscribe

interface MessagesStore : Subscribe<List<CloudMessage>>, EditMessage {

    fun addMessage(cloudMessage: CloudMessage)

    fun addMessages(messages: List<CloudMessage>)

    class Base(
        private val listItem: ListItem<CloudMessage>,
        private val mapper: ToCloudProfressMessageMapper
    ) : MessagesStore {

        private val messages = ArrayList<CloudMessage>()
        private var block : (List<CloudMessage>) -> Unit = {}

        override fun addMessage(cloudMessage: CloudMessage) {
            messages.add(cloudMessage)
            block.invoke(
                ArrayList(messages)
            )
        }

        override fun addMessages(messages: List<CloudMessage>) {
            this.messages.clear()
            this.messages.addAll(messages)
            block.invoke(
                ArrayList(messages)
            )
        }

        override suspend fun editMessage(messageId: String, content: String) {
            val messageById = listItem.item(messages,messageId)
            val editedMessageById = messageById.map(content,mapper)

            val indexMessageById = listItem.index(messages,messageId)
            this.messages[indexMessageById] = editedMessageById

            Log.d("zinoviewk","msg by id $messageById, index $indexMessageById")
            Log.d("zinoviewk","msg $messages")

            block.invoke(ArrayList(messages))
        }

        override fun subscribe(block: (List<CloudMessage>) -> Unit) {
            this.block = block
        }
    }
}