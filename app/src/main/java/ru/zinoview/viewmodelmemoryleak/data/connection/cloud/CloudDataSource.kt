package ru.zinoview.viewmodelmemoryleak.data.connection.cloud

import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.data.core.SuspendObserve
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.AbstractCloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.SocketConnection
import ru.zinoview.viewmodelmemoryleak.ui.core.NetworkConnection

interface CloudDataSource<T> :
    AbstractCloudDataSource,
    SuspendObserve<CloudConnection>,
    NetworkConnection<T> {

    class Base(
        private val socket: Socket,
        private val connection: SocketConnection,
        private val connectionState: ConnectionState,
    ) : CloudDataSource<Unit>, AbstractCloudDataSource.Base(socket, connection){

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

        override suspend fun connection(isConnected: Boolean)
            = connectionState.update(isConnected,socket)
    }
}