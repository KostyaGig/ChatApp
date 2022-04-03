package ru.zinoview.viewmodelmemoryleak.chat.ui

import androidx.recyclerview.widget.RecyclerView

interface ItemSizeListener {

    fun sizeChanged(size: Int)

    class Base(
        private val recyclerView: RecyclerView
    ) : ItemSizeListener {

        override fun sizeChanged(size: Int) {
            recyclerView.postDelayed({
                recyclerView.scrollToPosition(size)
            }, 1000)
        }
    }
}