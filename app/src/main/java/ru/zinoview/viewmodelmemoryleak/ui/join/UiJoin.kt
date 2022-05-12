package ru.zinoview.viewmodelmemoryleak.ui.join

import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBar
import ru.zinoview.viewmodelmemoryleak.ui.core.Navigate
import ru.zinoview.viewmodelmemoryleak.ui.core.ShowError
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.Navigation
import ru.zinoview.viewmodelmemoryleak.ui.users.UsersFragment

interface UiJoin : Navigate<Navigation>, ShowError {

    override fun navigate(navigation: Navigation) = Unit
    override fun showError(snackBar: SnackBar<String>) = Unit

    object Success : UiJoin {

        override fun navigate(navigation: Navigation)
            = navigation.navigateTo(UsersFragment())
    }

    class Failure(
        private val message: String
    ) : UiJoin {
        override fun showError(snackBar: SnackBar<String>)
            = snackBar.show(message)
    }
}
