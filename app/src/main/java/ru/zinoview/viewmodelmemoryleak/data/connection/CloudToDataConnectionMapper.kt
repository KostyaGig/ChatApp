package ru.zinoview.viewmodelmemoryleak.data.connection

import ru.zinoview.viewmodelmemoryleak.core.connection.Connection

class CloudToDataConnectionMapper : Connection.Mapper<DataConnection> {

    override fun map() = DataConnection.Success
    override fun map(message: String) = DataConnection.Failure(message)
}