package ru.zinoview.viewmodelmemoryleak.chat.data.connection

import ru.zinoview.viewmodelmemoryleak.chat.core.Connection

class CloudToDataConnectionMapper : Connection.Mapper<DataConnection> {

    override fun map() = DataConnection.Success
    override fun map(message: String) = DataConnection.Failure(message)
}