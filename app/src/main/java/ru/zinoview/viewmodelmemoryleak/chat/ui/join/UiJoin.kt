package ru.zinoview.viewmodelmemoryleak.chat.ui.join

import ru.zinoview.viewmodelmemoryleak.chat.ui.core.navigation.Navigation
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.ChatFragment
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view.SnackBar
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Navigate
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.ShowError

interface UiJoin : Navigate, ShowError {

    override fun navigate(navigation: Navigation) = Unit
    override fun showError(snackBar: SnackBar) = Unit

    object Success : UiJoin {

        override fun navigate(navigation: Navigation)
            = navigation.navigateTo(ChatFragment())
    }

    class Failure(
        private val message: String
    ) : UiJoin {
        override fun showError(snackBar: SnackBar)
            = snackBar.show(message)
    }
}
