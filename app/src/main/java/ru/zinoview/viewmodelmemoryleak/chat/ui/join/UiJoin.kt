package ru.zinoview.viewmodelmemoryleak.chat.ui.join

import ru.zinoview.viewmodelmemoryleak.chat.core.navigation.Navigation
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.ChatFragment
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Navigate

interface UiJoin : Navigate {

    override fun navigate(navigation: Navigation) = Unit

    object Success : UiJoin {

        override fun navigate(navigation: Navigation)
            = navigation.navigateTo(ChatFragment())
    }

    object Failure : UiJoin
}
