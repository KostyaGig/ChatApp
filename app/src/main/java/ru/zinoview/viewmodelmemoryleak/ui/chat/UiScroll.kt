package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.util.Log
import androidx.recyclerview.widget.RecyclerView

interface UiScroll {

    fun scroll(recyclerView: RecyclerView, listener: ChatRecyclerViewScrollListener)

    class Base : UiScroll {
        override fun scroll(
            recyclerView: RecyclerView,
            listener: ChatRecyclerViewScrollListener
        ) {
            Log.d("zinoviewk","ADD SCROLLL")
            listener.onScrolled(recyclerView,0,0)
        }
    }

    class Empty() : UiScroll {
        override fun scroll(
            recyclerView: RecyclerView,
            listener: ChatRecyclerViewScrollListener
        ) = Unit
    }
}
