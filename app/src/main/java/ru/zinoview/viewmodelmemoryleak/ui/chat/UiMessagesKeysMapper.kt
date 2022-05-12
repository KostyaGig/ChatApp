package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.view.ViewGroup
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.EditMessageListener
import ru.zinoview.viewmodelmemoryleak.ui.core.ViewInflater
import java.lang.IllegalStateException

interface UiMessagesKeysMapper {

    fun map(key: Int,parent: ViewGroup,listener: EditMessageListener) : ChatAdapter.BaseViewHolder

    fun map(message: UiMessage) : Int

    class Base(
        private val inflater: ViewInflater
    ) : UiMessagesKeysMapper {

        override fun map(key: Int,parent: ViewGroup,listener: EditMessageListener) : ChatAdapter.BaseViewHolder {
            return when(key) {
                1 -> ChatAdapter.BaseViewHolder.Message(
                    inflater.inflate(parent,R.layout.sent_read),
                    listener
                )
                2 -> ChatAdapter.BaseViewHolder.Message(
                    inflater.inflate(parent,R.layout.sent_unread),
                    listener
                )
                3 -> ChatAdapter.BaseViewHolder.Message(
                    inflater.inflate(parent,R.layout.progress),
                    listener
                )
                4 -> ChatAdapter.BaseViewHolder.Message(
                    inflater.inflate(parent,R.layout.received),
                    listener
                )
                5 -> ChatAdapter.BaseViewHolder.Empty(
                    inflater.inflate(parent,R.layout.empty),
                )
                6 -> ChatAdapter.BaseViewHolder.Typing(
                    inflater.inflate(parent,R.layout.typing),
                )
                7 -> ChatAdapter.BaseViewHolder.Message(
                    inflater.inflate(parent,R.layout.found_received),
                    listener
                )
                8-> ChatAdapter.BaseViewHolder.Failure(
                    inflater.inflate(parent,R.layout.error),
                )
                else -> throw IllegalStateException("UiMessagesKeysMapper.map else branch doesn't handle $key")
            }
        }

        override fun map(message: UiMessage): Int {
            return when(message) {
                is UiMessage.Sent.Read -> READ
                is UiMessage.Sent.Unread -> UNREAD
                is UiMessage.ProgressMessage -> PROGRESS
                is UiMessage.Received.Base -> RECEIVED
                is UiMessage.Progress, UiMessage.Empty, is UiMessage.Typing.IsNot,is UiMessage.Empty,  -> EMPTY
                is UiMessage.Typing.Is -> TYPING
                is UiMessage.Received.Found -> FOUND
                is UiMessage.Failure -> FAILURE
                else -> DEFAULT
            }
        }

        private companion object {
            private const val READ = 1
            private const val UNREAD = 2
            private const val PROGRESS = 3
            private const val RECEIVED = 4
            private const val EMPTY = 5
            private const val TYPING = 6
            private const val FOUND = 7
            private const val FAILURE = 8

            private const val DEFAULT = 8
        }
    }
}