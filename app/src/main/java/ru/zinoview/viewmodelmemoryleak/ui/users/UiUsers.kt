package ru.zinoview.viewmodelmemoryleak.ui.users

import ru.zinoview.viewmodelmemoryleak.core.Update
import ru.zinoview.viewmodelmemoryleak.ui.core.Adapter
import ru.zinoview.viewmodelmemoryleak.ui.core.ChangeTitle
import ru.zinoview.viewmodelmemoryleak.ui.core.ToolbarActivity

sealed class UiUsers : ChangeTitle<ToolbarActivity>, Update<Adapter<List<UiUser>>> {

    data class Success(
            private val users: List<UiUser>,
            private val toolbarTitle: String
        ) : UiUsers() {

        override fun update(adapter: Adapter<List<UiUser>>)
            = adapter.update(users)

        override fun changeTitle(toolbar: ToolbarActivity)
            = toolbar.changeTitle(toolbarTitle)
        }

    data class Failure(
        private val message: String
    ) : UiUsers() {
        override fun changeTitle(toolbar: ToolbarActivity) = toolbar.changeTitle(message)
        override fun update(adapter: Adapter<List<UiUser>>) = Unit
    }
}