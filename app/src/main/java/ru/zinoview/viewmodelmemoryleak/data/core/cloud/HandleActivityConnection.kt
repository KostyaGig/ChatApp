package ru.zinoview.viewmodelmemoryleak.data.core.cloud

import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.data.connection.cloud.ConnectionState

interface HandleActivityConnection {

    fun handleActivityConnection(socket: Socket, connectionState: ConnectionState)
}