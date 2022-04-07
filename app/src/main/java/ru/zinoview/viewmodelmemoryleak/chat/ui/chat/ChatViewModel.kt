package ru.zinoview.viewmodelmemoryleak.chat.ui.chat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.chat.ui.UiChatMessage
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Dispatcher

interface ChatViewModel {

    fun disconnect()

    fun sendMessage(content: String)

    fun observeMessages(block:(List<UiChatMessage>) -> Unit)

    class Base(
        private val cloudDataSource: CloudDataSource.SendMessage,
        private val dispatcher: Dispatcher
    ) : ViewModel(), ChatViewModel {

        // todo use livedata
        private var block: ((List<UiChatMessage>) -> Unit)? = null

        override fun sendMessage(content: String)
            = cloudDataSource.sendMessage(content)

        override fun observeMessages(block: (List<UiChatMessage>) -> Unit) {
            this.block = block
            cloudDataSource.observeMessages { messages ->
                dispatcher.doUi(viewModelScope) {
                    block.invoke(messages)
                }
            }
        }

        override fun disconnect() {
            block = null
            cloudDataSource.disconnect()
        }
    }
}