package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import ru.zinoview.viewmodelmemoryleak.core.Update

interface UiChatMessagesMemento : Update<List<UiMessage>>, FindItem<String> {

    class Base(
        private val mapper: ToUiFoundMessageMapper
    ) : UiChatMessagesMemento {

        private val actualMessages = ArrayList<UiMessage>()

        override fun update(data: List<UiMessage>) {
            this.actualMessages.clear()
            this.actualMessages.addAll(data)
        }

        override fun find(id: String,manager: RecyclerView.LayoutManager,recyclerView: RecyclerView,adapter: ChatAdapter) {
            var index = -1
            actualMessages.forEachIndexed{i, message ->
                if (message.sameId(id)) {
                    index = i
                }
            }

            if (index >= 0) {
                val foundMessage = actualMessages[index].map(mapper)
                actualMessages[index] = foundMessage
                Log.d("zinoviewk","found message $foundMessage, $actualMessages")
                manager.smoothScrollToPosition(recyclerView, RecyclerView.State(),index)
                adapter.submitList(actualMessages)
            }

        }

    }

    object Empty : UiChatMessagesMemento {
        override fun update(data: List<UiMessage>) = Unit
        override fun find(id: String, manager: RecyclerView.LayoutManager,recyclerView: RecyclerView,adapter: ChatAdapter) = Unit
    }
}