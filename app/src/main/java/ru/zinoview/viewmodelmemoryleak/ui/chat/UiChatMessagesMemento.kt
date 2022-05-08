package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import ru.zinoview.viewmodelmemoryleak.core.Update

interface UiChatMessagesMemento : Update<List<UiMessage>>, FindItem<String> {

    class Base(
        private val mapper: ToUiFoundMessageMapper,
        private val recyclerView: RecyclerView,
        private val adapter: ChatAdapter
    ) : UiChatMessagesMemento {

        private val actualMessages = ArrayList<UiMessage>()

        override fun update(data: List<UiMessage>) {
            this.actualMessages.clear()
            this.actualMessages.addAll(data)
        }

        override fun find(id: String) {
            Log.d("zinoviewk","FIND")
            var index = -1
            actualMessages.forEachIndexed{i, message ->
                if (message.same(id)) {
                    index = i
                }
            }

            if (index >= 0) {
                Log.d("zinoviewk","found message before ${actualMessages[index]}")
                val foundMessage = actualMessages[index].map(mapper)
                actualMessages[index] = foundMessage
                Log.d("zinoviewk","found message after $foundMessage, $actualMessages")
                recyclerView.layoutManager?.smoothScrollToPosition(recyclerView, RecyclerView.State(),index)
                Log.d("zinoviewk","UPDATE MEMENTO")
                adapter.submitList(actualMessages)
            }

        }

    }

    object Empty : UiChatMessagesMemento {
        override fun update(data: List<UiMessage>) = Unit
        override fun find(id: String) {
            Log.d("zinoviewk","EMPTY MEMENTO -> FIND()")
        }
    }
}