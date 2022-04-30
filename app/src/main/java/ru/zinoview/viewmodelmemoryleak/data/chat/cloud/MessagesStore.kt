package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty
import ru.zinoview.viewmodelmemoryleak.core.chat.EditMessage
import ru.zinoview.viewmodelmemoryleak.core.chat.ui_state.Messages
import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Subscribe

interface MessagesStore :
    Subscribe<List<CloudMessage>>,
    EditMessage,
    MessageStoreAdd,
    Messages<CloudMessage> {

    fun unreadMessageIds(range: Pair<Int, Int>, block: (List<String>) -> Unit)

    fun updateProcessingMessages(processingMessages: ProcessingMessages, messageId: String, content: String)

    class Base(
        private val listItem: ListItem<CloudMessage>,
        private val mapper: ToProgressEditMessageMapper,
        private val isNotEmpty: IsNotEmpty<List<CloudMessage>>,
        private val listSize: ListSize,
        private val idSharedPreferences: IdSharedPreferences<String,Unit>,
    ) : MessagesStore {

        private val messages = ArrayList<CloudMessage>()
        private var block : (List<CloudMessage>) -> Unit = {}

        override fun add(cloudMessage: CloudMessage) {
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

            block.invoke(ArrayList(messages))
        }

        override fun updateProcessingMessages(
            processingMessages: ProcessingMessages,
            messageId: String,
            content: String
        ) {

            Log.d("zinoviewk","msgId: $messageId,messages $messages")

            val messageById = listItem.item(messages,messageId)
            val editedMessageById = messageById.map(content,mapper)

            val progressEditMessage = editedMessageById.map(mapper)

            processingMessages.add(progressEditMessage)
        }

        override fun unreadMessageIds(range: Pair<Int, Int>, block: (List<String>) -> Unit) {
            val userId = idSharedPreferences.read(Unit)
            val unreadMessageIds = mutableListOf<String>()

            if (isNotEmpty.isNotEmpty(messages) && range.first != -1) {
                if (listSize.isLessThen(MIN_SIZE_LIST,messages)) {
                    val message = messages.first()
                    message.addUnreadMessageId(userId,unreadMessageIds)
                } else {
                    for (index in range.first..range.second) {
                        // todo
//                        if (messages.size - 1 >= index ) {
                            val message = messages[index]
                            message.addUnreadMessageId(userId,unreadMessageIds)
//                        }
                    }
                }
            }
            block.invoke(unreadMessageIds)
        }

        override fun messages(): List<CloudMessage> = ArrayList(messages)

        override fun subscribe(block: (List<CloudMessage>) -> Unit) {
            this.block = block
        }

        private companion object {
            private const val MIN_SIZE_LIST = 2
        }
    }
}