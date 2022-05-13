package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.EditMessageListener
import ru.zinoview.viewmodelmemoryleak.ui.core.AbstractAdapter
import ru.zinoview.viewmodelmemoryleak.ui.core.AbstractDiffUtil
import ru.zinoview.viewmodelmemoryleak.ui.core.AbstractViewHolder

class ChatAdapter(
     diffUtil: AbstractDiffUtil<UiMessage>,
     private val listener: EditMessageListener,
     private val mapper: UiMessagesKeysMapper
 ) : AbstractAdapter<UiMessage>(diffUtil) {

    override fun getItemViewType(position: Int)
        = mapper.map(getItem(position))

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = mapper.map(viewType,parent,listener)

     abstract class BaseViewHolder(
        view: View
    ) : AbstractViewHolder<UiMessage>(view) {

        class Message(
            view: View,
            private val listener: EditMessageListener
        ) : BaseViewHolder(view) {

            private val contentTv = view.findViewById<TextView>(R.id.message_content_tv)
            private val nicknameTextView = view.findViewById<TextView>(R.id.nickname_tv)
            private val stateImage = view.findViewById<ImageView>(R.id.state_send_image)
            private val editImage = view.findViewById<ImageView>(R.id.edit_image)

            override fun bind(item: UiMessage) {
                item.bind(contentTv,stateImage,editImage)
                item.bindNickName(nicknameTextView)
                editImage.setOnClickListener { item.onClick(listener) }
            }
        }

        class Empty(
            view: View
        ) : BaseViewHolder(view)

        class Failure(
            view: View
        ) : BaseViewHolder(view) {
            override fun bind(item: UiMessage) = Unit
        }

        class Typing(
            view: View
        ) : BaseViewHolder(view) {

            private val nicknameTextView = view.findViewById<TextView>(R.id.nickname_tv)
            override fun bind(item: UiMessage) = item.bind(nicknameTextView)
        }
    }
}
