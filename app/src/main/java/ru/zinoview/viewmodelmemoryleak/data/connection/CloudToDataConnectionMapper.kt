package ru.zinoview.viewmodelmemoryleak.data.connection

import ru.zinoview.viewmodelmemoryleak.core.connection.Connection

class CloudToDataConnectionMapper : Connection.Mapper<DataConnection> {

    override fun mapSuccess(message: String)
        = DataConnection.Success(message)

    override fun mapMessage(message: String)
        = DataConnection.Message(message)
}