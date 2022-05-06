package ru.zinoview.viewmodelmemoryleak.data.connection.cloud

import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketWrapper
import ru.zinoview.viewmodelmemoryleak.data.core.SuspendObserve
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.AbstractCloudDataSource
import ru.zinoview.viewmodelmemoryleak.ui.core.NetworkConnection

interface CloudDataSource<T> :
    AbstractCloudDataSource,
    SuspendObserve<CloudConnection>,
    NetworkConnection<T> {

    class Base(
        private val socketWrapper: SocketWrapper,
        private val connectionState: ConnectionState
    ) : CloudDataSource<Unit>, AbstractCloudDataSource.Base(socketWrapper){

        override suspend fun observe(block: (CloudConnection) -> Unit) {
            socketWrapper.connect(Socket.EVENT_DISCONNECT)
            socketWrapper.connect(Socket.EVENT_CONNECT)

            connectionState.subscribe(block)

            socketWrapper.subscribe(Socket.EVENT_CONNECT) {
                connectionState.connect(Unit)
            }

            socketWrapper.subscribe(Socket.EVENT_DISCONNECT) {
                connectionState.disconnect(Unit)
            }
        }

        override suspend fun connection(isConnected: Boolean)
            = connectionState.update(isConnected,socketWrapper)
    }
}