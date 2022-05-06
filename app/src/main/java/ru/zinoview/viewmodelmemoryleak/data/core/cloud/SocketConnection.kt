package ru.zinoview.viewmodelmemoryleak.data.core.cloud

import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketWrapper

interface SocketConnection : Disconnect<SocketWrapper>,ServerState, Connect<Socket> {

    class Base(
        private val activity: ActivityConnection
    ) : SocketConnection {

        override fun connect(socket: Socket) {
            val isActive = socket.isActive
            val idServer = socket.id()

            if(isActive.not() || idServer == null) {
                socket.connect()
            }
        }

        override suspend fun serverState(socket: SocketWrapper) : CloudServerState
            = activity.serverState(socket)

        override fun disconnect(socket: SocketWrapper)
            = socket.disconnect(Unit)
    }
}