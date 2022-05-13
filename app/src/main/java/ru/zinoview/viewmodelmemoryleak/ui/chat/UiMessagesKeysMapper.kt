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
                READ -> ChatAdapter.BaseViewHolder.Message(
                    inflater.inflate(parent,R.layout.sent_read),
                    listener
                )
                UNREAD -> ChatAdapter.BaseViewHolder.Message(
                    inflater.inflate(parent,R.layout.sent_unread),
                    listener
                )
                PROGRESS -> ChatAdapter.BaseViewHolder.Message(
                    inflater.inflate(parent,R.layout.progress),
                    listener
                )
                RECEIVED -> ChatAdapter.BaseViewHolder.Message(
                    inflater.inflate(parent,R.layout.received),
                    listener
                )
                EMPTY -> ChatAdapter.BaseViewHolder.Empty(
                    inflater.inflate(parent,R.layout.empty),
                )
                TYPING -> ChatAdapter.BaseViewHolder.Typing(
                    inflater.inflate(parent,R.layout.typing),
                )
                FOUND -> ChatAdapter.BaseViewHolder.Message(
                    inflater.inflate(parent,R.layout.found_received),
                    listener
                )
                FAILURE-> ChatAdapter.BaseViewHolder.Failure(
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