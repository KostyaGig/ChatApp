package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class ChatRecyclerViewScrollListener : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) = Unit

    class Base(
        private val manager: LinearLayoutManager,
        private val viewModel: ChatViewModel
    ) : ChatRecyclerViewScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val firstVisibleItemPosition = manager.findFirstCompletelyVisibleItemPosition()
            val lastVisibleItemPosition = manager.findLastCompletelyVisibleItemPosition()

            Log.d("zinoviewk","first $firstVisibleItemPosition, last $lastVisibleItemPosition")

            viewModel.updateMessagesState(
                Pair(firstVisibleItemPosition,lastVisibleItemPosition)
            )
        }
    }
    object Empty : ChatRecyclerViewScrollListener()

}