package ru.zinoview.viewmodelmemoryleak.ui.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractViewHolder<T : DiffSame<T>>(view: View) : RecyclerView.ViewHolder(view) {

    open fun bind(item: T) = Unit
}