package ru.zinoview.viewmodelmemoryleak.ui.connection

import ru.zinoview.viewmodelmemoryleak.core.connection.Connection

class DataToUiConnectionMapper : Connection.Mapper<UiConnection> {

    override fun map(message: String): UiConnection
        = UiConnection.Failure(message)

    override fun map(): UiConnection = UiConnection.Success(SUCCESS_CONNECTION_TITLE)

    private companion object {
        private const val SUCCESS_CONNECTION_TITLE = "Chat"
    }


}