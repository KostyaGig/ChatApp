package ru.zinoview.viewmodelmemoryleak.data.chat.cloud
import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.Add
import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty
import ru.zinoview.viewmodelmemoryleak.core.Update
import ru.zinoview.viewmodelmemoryleak.ui.core.Show
import ru.zinoview.viewmodelmemoryleak.ui.chat.notification.ShowNotification
import java.lang.IllegalArgumentException

interface ProcessingMessages : Show<Unit>, Add<CloudMessage>, Update<List<CloudMessage>> {

    class Base(
        private val isNotEmpty: IsNotEmpty<List<CloudMessage>>,
        private val add: ProcessingMessagesAdd,
        private val filter: Filter<CloudMessage>,
        private val notification: ShowNotification
    ): ProcessingMessages {

        private var messages: MutableList<CloudMessage> = mutableListOf()

        override fun add(data: CloudMessage) {
            add.add(messages,data)
        }

        override fun update(messages: List<CloudMessage>) {
            val filteredMessages = filter.filtered(this.messages,messages)
            this.messages.clear()
            this.messages.addAll(filteredMessages)
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