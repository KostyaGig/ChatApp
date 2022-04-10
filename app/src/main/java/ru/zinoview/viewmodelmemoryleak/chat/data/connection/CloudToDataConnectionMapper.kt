package ru.zinoview.viewmodelmemoryleak.chat.data.connection

import ru.zinoview.viewmodelmemoryleak.chat.core.Connection

class CloudToDataConnectionMapper : Connection.Mapper<DataConnection> {

    override fun mapConnection() = DataConnection.Connection
    override fun mapDisconnection(message: String) = DataConnection.Disconnection(message)
    override fun mapToolbarConnection(message: String): DataConnection = DataConnection.ToolbarConnection(message)
}