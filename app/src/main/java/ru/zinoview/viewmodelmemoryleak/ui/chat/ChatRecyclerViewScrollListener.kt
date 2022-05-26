package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.zinoview.viewmodelmemoryleak.ui.BundleUser

abstract class ChatRecyclerViewScrollListener : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) = Unit

    class Base(
        private val manager: LinearLayoutManager,
        private val viewModel: ChatViewModel,
        private val bundleUser: BundleUser
    ) : ChatRecyclerViewScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val firstVisibleItemPosition = manager.findFirstCompletelyVisibleItemPosition()
            val lastVisibleItemPosition = manager.findLastCompletelyVisibleItemPosition()

            Log.d("zinoviewk","first $firstVisibleItemPosition, last $lastVisibleItemPosition")

            val range = Pair(firstVisibleItemPosition,lastVisibleItemPosition)
            bundleUser.readMessages(range, viewModel)
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            Log.d("zinoviewk","onscroll state changed")
        }
    }
    object Empty : ChatRecyclerViewScrollListener()

}