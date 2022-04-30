package ru.zinoview.viewmodelmemoryleak.ui.chat

import androidx.recyclerview.widget.RecyclerView

interface UiScroll {

    fun addScrollListener(recyclerView: RecyclerView,listener: ChatRecyclerViewScrollListener)

    class Base : UiScroll {
        override fun addScrollListener(
            recyclerView: RecyclerView,
            listener: ChatRecyclerViewScrollListener
        ) {
            recyclerView.addOnScrollListener(listener)
            listener.onScrolled(recyclerView,0,0)
        }
    }

    class Empty() : UiScroll {
        override fun addScrollListener(
            recyclerView: RecyclerView,
            listener: ChatRecyclerViewScrollListener
        ) = Unit
    }
}
