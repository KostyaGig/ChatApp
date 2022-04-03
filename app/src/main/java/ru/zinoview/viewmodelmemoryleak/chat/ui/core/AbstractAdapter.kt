package ru.zinoview.viewmodelmemoryleak.chat.ui.core

import androidx.recyclerview.widget.ListAdapter

abstract class AbstractAdapter<T : DiffSame<T>>(
    callback: AbstractDiffUtil<T>
) : ListAdapter<T, AbstractViewHolder<T>>(callback)