package ru.zinoview.viewmodelmemoryleak.ui.chat.edit

import ru.zinoview.viewmodelmemoryleak.databinding.ChatFragmentBinding
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiMessage
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.ItemClickListener

interface EditMessageListener : ItemClickListener<UiMessage> {

    class Base(
        private val binding: ChatFragmentBinding,
        private val messageSession: MessageSession
    ) : EditMessageListener {

        override fun onClick(message: UiMessage) {
            val text = ViewWrapper.Text(binding.oldMessageTv)
            message.show(text)

            messageSession.show(Unit)
            messageSession.add(message)
        }

    }

    object Empty : EditMessageListener {
        override fun onClick(item: UiMessage) = Unit
    }
}