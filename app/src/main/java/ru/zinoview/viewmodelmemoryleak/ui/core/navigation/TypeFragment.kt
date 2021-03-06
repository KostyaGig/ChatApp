package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatFragment
import ru.zinoview.viewmodelmemoryleak.ui.join.JoinUserFragment
import ru.zinoview.viewmodelmemoryleak.ui.users.UsersFragment

interface TypeFragment{

    fun navigate(navigation: Navigation,notificationMessageId: String = "")

    object Join : TypeFragment {
        override fun navigate(navigation: Navigation, notificationMessageId: String)
            = navigation.navigateTo(JoinUserFragment(),NavigationData.Empty)
    }

    object Users : TypeFragment {
        override fun navigate(navigation: Navigation, notificationMessageId: String)
            = navigation.navigateTo(UsersFragment(),NavigationData.Empty)
    }

    object Chat : TypeFragment {

        override fun navigate(navigation: Navigation, notificationMessageId: String)
            = navigation.navigateTo(ChatFragment(),NavigationData.Empty)
    }
}