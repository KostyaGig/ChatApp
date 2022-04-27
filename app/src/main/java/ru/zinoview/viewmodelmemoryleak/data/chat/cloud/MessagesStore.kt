package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty
import ru.zinoview.viewmodelmemoryleak.core.chat.EditMessage
import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.chat.state.UiStateSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Subscribe
import ru.zinoview.viewmodelmemoryleak.ui.chat.ToUiMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.state.UiStates

interface MessagesStore :
    Subscribe<List<CloudMessage>>,
    EditMessage,
    // todo remove save state if it isn't used
    ru.zinoview.viewmodelmemoryleak.core.chat.state.SaveState,
    MessageStoreAdd {

    fun unreadMessageIds(range: Pair<Int, Int>, block: (List<String>) -> Unit)

    // todo move to update interface
    fun updateProcessingMessages(processingMessages: ProcessingMessages, messageId: String, content: String)

    class Base(
        private val listItem: ListItem<CloudMessage>,
        private val mapper: ToCloudProgressMessageMapper,
        private val isNotEmpty: IsNotEmpty<List<CloudMessage>>,
        private val listSize: ListSize,
        private val idSharedPreferences: IdSharedPreferences<Int,Unit>,
        private val uiMessageMapper: ToUiMessageMapper
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

            val messageById = listItem.item(messages,messageId)
            val editedMessageById = messageById.map(content,mapper)

            processingMessages.add(editedMessageById)
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
                        val message = messages[index]
                        message.addUnreadMessageId(userId,unreadMessageIds)
                    }
                }
            }
            block.invoke(unreadMessageIds)
        }

        override fun saveState(prefs: UiStateSharedPreferences, state: UiStates) {
            val newState = state.map(uiMessageMapper,messages)
            prefs.save(newState)
        }

        override fun subscribe(block: (List<CloudMessage>) -> Unit) {
            this.block = block
        }

        private companion object {
            private const val MIN_SIZE_LIST = 2
        }
    }
}