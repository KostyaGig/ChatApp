package ru.zinoview.viewmodelmemoryleak.ui.chat

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.zinoview.viewmodelmemoryleak.core.Add
import ru.zinoview.viewmodelmemoryleak.core.Clean

interface Scroll : ObserveScroll, Add<List<UiMessage>>, Clean {

    class Base(
        private val scrollCommunication: ScrollCommunication
    ) : Scroll {

        private var isNotAdded = true

        override fun observeScrollCommunication(
            owner: LifecycleOwner,
            observer: Observer<UiScroll>
        ) = scrollCommunication.observe(owner, observer)

        override fun add(messages: List<UiMessage>) {
            if (isNotAdded) {
                val first = messages.first()
                if (first is UiMessage.Received || first is UiMessage.Sent && messages.size == 1) {
                    scrollCommunication.postValue(UiScroll.Base())
                    isNotAdded = false
                }
            }
        }

        override fun clean() {
            isNotAdded = true
        }

    }

}