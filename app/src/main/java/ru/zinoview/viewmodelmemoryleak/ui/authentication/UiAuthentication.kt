package ru.zinoview.viewmodelmemoryleak.ui.authentication

import android.content.Context
import ru.zinoview.viewmodelmemoryleak.ui.core.MainActivity
import ru.zinoview.viewmodelmemoryleak.ui.core.Navigate
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.ActivityNavigation
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.TypeFragment

interface UiAuthentication : Navigate<ActivityNavigation> {

    override fun navigate(navigation: ActivityNavigation) = Unit

    object Success : UiAuthentication {

        override fun navigate(navigation: ActivityNavigation)
            = navigation.navigateTo(MainActivity(), TypeFragment.Chat)

    }

    object Failure : UiAuthentication {
        override fun navigate(navigation: ActivityNavigation)
            = navigation.navigateTo(MainActivity(),TypeFragment.Join)
    }
}