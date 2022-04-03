package ru.zinoview.viewmodelmemoryleak.chat.ui.core

import androidx.recyclerview.widget.DiffUtil

abstract class AbstractDiffUtil<T : DiffSame<T>> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T)
        = oldItem.isItemTheSame(newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T)
        = oldItem.isContentTheSame(newItem)
}