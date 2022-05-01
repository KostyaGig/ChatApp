package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatFragment
import ru.zinoview.viewmodelmemoryleak.ui.join.JoinUserFragment

interface TypeFragment{

    fun navigate(navigation: Navigation,notificationMessageId: String = "")

    object Join : TypeFragment {
        override fun navigate(navigation: Navigation, notificationMessageId: String)
            = navigation.navigateTo(JoinUserFragment())
    }

    object Chat : TypeFragment {

        override fun navigate(navigation: Navigation, notificationMessageId: String)
            = navigation.navigateTo(ChatFragment(),notificationMessageId)
    }
}