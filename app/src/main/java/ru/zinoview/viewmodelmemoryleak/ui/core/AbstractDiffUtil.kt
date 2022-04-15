package ru.zinoview.viewmodelmemoryleak.ui.core

import androidx.recyclerview.widget.DiffUtil
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiChatMessage

abstract class AbstractDiffUtil<T : DiffSame<T>> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T)
        = oldItem.isItemTheSame(newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T)
        = oldItem.isContentTheSame(newItem)

    object Empty : AbstractDiffUtil<UiChatMessage>()
}