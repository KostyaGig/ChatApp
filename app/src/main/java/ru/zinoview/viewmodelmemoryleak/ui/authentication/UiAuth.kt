package ru.zinoview.viewmodelmemoryleak.ui.authentication

import ru.zinoview.viewmodelmemoryleak.ui.core.MainActivity
import ru.zinoview.viewmodelmemoryleak.ui.core.Navigate
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.ActivityNavigation
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.TypeFragment

interface UiAuth : Navigate<ActivityNavigation> {

    override fun navigate(navigation: ActivityNavigation) = Unit

    object Success : UiAuth {

        override fun navigate(navigation: ActivityNavigation)
            = navigation.navigateTo(MainActivity(), TypeFragment.Chat)
    }

    object Failure : UiAuth {
        override fun navigate(navigation: ActivityNavigation)
            = navigation.navigateTo(MainActivity(),TypeFragment.Join)

    }
}