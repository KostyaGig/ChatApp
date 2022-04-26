package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.Add
import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty
import ru.zinoview.viewmodelmemoryleak.ui.core.Show
import java.lang.IllegalArgumentException

interface ProcessingMessages : Show<Unit>, Add<CloudMessage> {

    fun update(messages: List<CloudMessage>)

    class Base(
        private val isNotEmpty: IsNotEmpty<List<CloudMessage>>,
        private val add: ProcessingMessagesAdd,
        private val filter: Filter<CloudMessage>,
        private val notification: MessagesNotification
    ): ProcessingMessages {

        private var messages: MutableList<CloudMessage> = mutableListOf()

        override fun add(data: CloudMessage) {
            add.add(messages,data)
            Log.d("zinoviewk","after add process $messages")
        }

        // todo move to interface and compare with other methods of this project
        override fun update(messages: List<CloudMessage>) {
//            Log.d("zinoviewk","update messages $messages, src ${this.messages}")
            val old = messages
            val filteredMessages = filter.filtered(this.messages,messages)
            this.messages.clear()
            this.messages.addAll(filteredMessages)
            Log.d("zinoviewk","update process messages before filter $old after ${this.messages}")
        }

        override fun show(arg: Unit) {
            if (isNotEmpty.isNotEmpty(messages)) {
                notification.show(messages)
            }
        }
    }

    object Empty : ProcessingMessages {
        override fun update(messages: List<CloudMessage>)
            = throw IllegalArgumentException("ProcessingMessages.Empty -> update()")
        override fun show(arg: Unit)
            = throw IllegalArgumentException("ProcessingMessages.Empty -> show()")
        override fun add(data: CloudMessage)
            = throw IllegalArgumentException("ProcessingMessages.Empty -> add()")
        }

}