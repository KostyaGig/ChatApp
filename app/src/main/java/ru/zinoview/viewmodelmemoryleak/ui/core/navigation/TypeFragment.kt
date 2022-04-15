package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatFragment
import ru.zinoview.viewmodelmemoryleak.ui.core.Navigate
import ru.zinoview.viewmodelmemoryleak.ui.join.JoinUserFragment

interface TypeFragment : Navigate<Navigation> {

    object Join : TypeFragment {

        override fun navigate(navigation: Navigation)
            = navigation.navigateTo(JoinUserFragment())
    }

    object Chat : TypeFragment {

        override fun navigate(navigation: Navigation)
            = navigation.navigateTo(ChatFragment())
    }
}