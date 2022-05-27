package ru.zinoview.viewmodelmemoryleak.ui.connection

import ru.zinoview.viewmodelmemoryleak.ui.core.*

interface UiConnection : ChangeTitle<ToolbarActivity>, SameOne<UiConnection> {


    override fun changeTitle(arg: ToolbarActivity) = Unit
    override fun same(data: UiConnection) = false
    fun doAction(action: () -> Unit) = Unit

    data class Success(
        private val message: String
    ) : UiConnection {

        override fun changeTitle(toolbar: ToolbarActivity) = toolbar.changeTitle(message)

        override fun doAction(action: () -> Unit) = action.invoke()

        override fun same(data: UiConnection) = data is Success
    }

    data class Message(private val message: String) : UiConnection {

        override fun changeTitle(toolbar: ToolbarActivity) = toolbar.changeTitle(message)

        override fun same(data: UiConnection) = false
    }

    object Empty : UiConnection
}