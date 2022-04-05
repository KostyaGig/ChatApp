package ru.zinoview.viewmodelmemoryleak.chat.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.AbstractDiffUtil
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.AbstractViewHolder
import java.lang.IllegalStateException

 class ChatAdapter(
     diffUtil: AbstractDiffUtil<UiChatMessage>
 ) : androidx.recyclerview.widget.ListAdapter<UiChatMessage, ChatAdapter.ChatViewHolder>(diffUtil) {

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
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
            )
            2 -> ChatViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.received,parent,false),
            )
            else -> throw IllegalStateException("ChatAdapter.onCreateViewHolder else branch")
        }
    }

     override fun onBindViewHolder(holder: ChatViewHolder, position: Int)
        = holder.bind(getItem(position))

    class ChatViewHolder(
            view: View,
        ) : AbstractViewHolder<UiChatMessage>(view) {

        private val contentTv = view.findViewById<TextView>(R.id.message_content_tv)

        override fun bind(item: UiChatMessage) {
            item.bind(contentTv)
        }
    }

}
