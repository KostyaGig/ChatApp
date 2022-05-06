package ru.zinoview.viewmodelmemoryleak.ui.chat

import androidx.recyclerview.widget.RecyclerView

interface FindItem<T> {

    fun find(id: T,manager: RecyclerView.LayoutManager,recyclerView: RecyclerView,adapter: ChatAdapter)
}