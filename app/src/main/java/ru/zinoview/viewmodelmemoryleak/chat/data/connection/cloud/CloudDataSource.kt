package ru.zinoview.viewmodelmemoryleak.chat.data.connection.cloud

import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.chat.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.chat.core.SuspendObserve
import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.AbstractCloudDataSource
import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.HandleActivityConnection
import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.SocketConnection
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.CheckNetworkConnection

interface CloudDataSource :
    AbstractCloudDataSource,
    SuspendObserve<CloudConnection>,
    HandleActivityConnection,
    CheckNetworkConnection {

    class Base(
        private val socket: Socket,
        private val connection: SocketConnection,
        private val connectionState: ConnectionState,
        private val resourceProvider: ResourceProvider
    ) : CloudDataSource, AbstractCloudDataSource.Base(socket, connection){

        override suspend fun observe(block: (CloudConnection) -> Unit) {
            connection.connect(socket)
            connectionState.subscribe(block)

            socket.on(Socket.EVENT_CONNECT) {
                block.invoke(CloudConnection.Connect)
            }

            socket.on(Socket.EVENT_DISCONNECT) {
                connectionState.change(false,resourceProvider.string(R.string.waiting_for_server))
            }

            connection.handleActivityConnection(socket) {
                block.invoke(
                    CloudConnection.Disconnect(
                        resourceProvider.string(R.string.waiting_for_server)
                    )
                )
            }

            connection.addSocketBranch(Socket.EVENT_DISCONNECT)
            connection.addSocketBranch(Socket.EVENT_CONNECT)
        }

        override fun checkNetworkConnection(state: Boolean)
            = connectionState.change(state,resourceProvider.string(R.string.waiting_for_network))

        override suspend fun handleActivityConnection(socket: Socket, isNotActive: () -> Unit)
            = connection.handleActivityConnection(socket, isNotActive)

    }
}