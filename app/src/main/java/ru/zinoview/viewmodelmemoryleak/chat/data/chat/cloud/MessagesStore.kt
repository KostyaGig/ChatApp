package ru.zinoview.viewmodelmemoryleak.chat.data.chat.cloud

import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.Subscribe

interface MessagesStore : Subscribe<List<CloudMessage>> {

    fun addMessage(cloudMessage: CloudMessage)

    fun addMessages(messages: List<CloudMessage>)

    class Base : MessagesStore {

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

        override fun subscribe(block: (List<CloudMessage>) -> Unit) {
            this.block = block
        }
    }
}