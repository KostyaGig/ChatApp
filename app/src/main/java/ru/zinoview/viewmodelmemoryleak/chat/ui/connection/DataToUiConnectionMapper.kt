package ru.zinoview.viewmodelmemoryleak.chat.ui.connection

import ru.zinoview.viewmodelmemoryleak.chat.core.Connection

class DataToUiConnectionMapper : Connection.Mapper<UiConnection> {
    override fun mapConnection(): UiConnection
        = UiConnection.Connection(CONNECTION)

    override fun mapDisconnection(message: String): UiConnection
        = UiConnection.Disconnection(message)

    override fun mapToolbarDisconnection(message: String): UiConnection
        = UiConnection.ToolbarDisconnection(message)

    private companion object {
        private const val CONNECTION = "Chat"
    }
}