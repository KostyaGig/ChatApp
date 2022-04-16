package ru.zinoview.viewmodelmemoryleak.data.connection.cloud

import android.util.Log
import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.data.core.SuspendObserve
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.AbstractCloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.HandleActivityConnection
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.SocketConnection
import ru.zinoview.viewmodelmemoryleak.ui.core.UpdateNetworkConnection

interface CloudDataSource :
    AbstractCloudDataSource,
    SuspendObserve<CloudConnection>,
    UpdateNetworkConnection {

    class Base(
        private val socket: Socket,
        private val connection: SocketConnection,
        private val connectionState: ConnectionState
    ) : CloudDataSource, AbstractCloudDataSource.Base(socket, connection){

        override suspend fun observe(block: (CloudConnection) -> Unit) {
            connection.connect(socket)
            connectionState.subscribe(block)

            socket.on(Socket.EVENT_CONNECT) {
                connectionState.connect(Unit)
            }

            socket.on(Socket.EVENT_DISCONNECT) {
                connectionState.disconnect(Unit)
            }

            connection.addSocketBranch(Socket.EVENT_DISCONNECT)
            connection.addSocketBranch(Socket.EVENT_CONNECT)
        }

        override suspend fun updateNetworkConnection(isConnected: Boolean)
            = connectionState.update(isConnected)
    }
}