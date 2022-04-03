package ru.zinoview.viewmodelmemoryleak.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.chat.ui.ItemSizeListener
import ru.zinoview.viewmodelmemoryleak.chat.ui.UiChatMessage
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.AbstractAdapter
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.AbstractDiffUtil
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.AbstractViewHolder
import java.lang.IllegalStateException

 class ChatAdapter(
     diffUtil: AbstractDiffUtil<UiChatMessage>,
     private val itemSizeListener: ItemSizeListener
 ) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private val messages = ArrayList<UiChatMessage>()

    fun updateList(list: List<UiChatMessage>) {
        messages.clear()
        messages.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when(messages[position]) {
            is UiChatMessage.Sent -> 1
            is UiChatMessage.Received -> 2
            else -> -1
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatViewHolder {
        return when(viewType) {
            1 -> ChatViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.sent,parent,false),
                itemSizeListener,
                messages.size
            )
            2 -> ChatViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.received,parent,false),
                itemSizeListener,
                messages.size
            )
            else -> throw IllegalStateException("ChatAdapter.onCreateViewHolder else branch")
        }
    }

//    override fun onBindViewHolder(holder: AbstractViewHolder<UiChatMessage>, position: Int)
//        =

    class ChatViewHolder(
            view: View,
            private val sizeListener: ItemSizeListener,
            private val size: Int
        ) : AbstractViewHolder<UiChatMessage>(view) {

        private val contentTv = view.findViewById<TextView>(R.id.message_content_tv)

        override fun bind(item: UiChatMessage) {
            item.bind(contentTv)
            sizeListener.sizeChanged(size)
        }
    }

    override fun getItemCount() = messages.size
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) = holder.bind(messages[position])

}
