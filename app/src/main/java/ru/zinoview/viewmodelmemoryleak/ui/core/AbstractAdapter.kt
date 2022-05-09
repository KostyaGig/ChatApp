package ru.zinoview.viewmodelmemoryleak.ui.core

import androidx.recyclerview.widget.ListAdapter

abstract class AbstractAdapter<T : DiffSame<T>>(
    callback: AbstractDiffUtil<T>
) : ListAdapter<T, AbstractViewHolder<T>>(callback), Adapter<List<T>> {

    override fun update(data: List<T>) = submitList(data)

    override fun onBindViewHolder(holder: AbstractViewHolder<T>, position: Int)
        = holder.bind(getItem(position))
}