package ru.zinoview.viewmodelmemoryleak.chat.ui.authentication

import ru.zinoview.viewmodelmemoryleak.chat.core.navigation.Navigation
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.ChatFragment
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Navigate
import ru.zinoview.viewmodelmemoryleak.chat.ui.join.JoinUserFragment

interface UiAuthentication : Navigate {

    override fun navigate(navigation: Navigation) = Unit

    object Success : UiAuthentication {

        override fun navigate(navigation: Navigation)
            = navigation.navigateTo(ChatFragment())
    }

    object Failure : UiAuthentication {
        override fun navigate(navigation: Navigation)
            = navigation.navigateTo(JoinUserFragment())
    }
}