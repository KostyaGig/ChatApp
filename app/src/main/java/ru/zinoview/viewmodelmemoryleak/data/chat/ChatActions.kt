package ru.zinoview.viewmodelmemoryleak.data.chat
import ru.zinoview.viewmodelmemoryleak.data.chat.workmanager.Worker

interface ChatActions{

    fun sendMessage(data: List<String>)

    fun editMessage(data: List<String>)

    class Base(
        private val worker: Worker,
        private val send: ChatAction,
        private val edit: ChatAction
    ) : ChatActions {

        override fun sendMessage(data: List<String>)
            = send.executeWorker(worker, data)

        override fun editMessage(data: List<String>)
            = edit.executeWorker(worker, data)

    }
}