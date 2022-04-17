package ru.zinoview.viewmodelmemoryleak.data.connection.cloud

import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Connect
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.SocketConnection
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Subscribe

interface ConnectionState : Subscribe<CloudConnection>, Disconnect<Unit>, Connect<Unit> {

    suspend fun update(isConnected: Boolean,socket: Socket)

    fun push(state: CloudConnection)

    class Base(
        private val connection: SocketConnection,
        private val resourceProvider: ResourceProvider
    ) : ConnectionState {

        private var block: (CloudConnection) -> Unit = {}

        override fun disconnect(arg: Unit)
            = push(CloudConnection.Failure(resourceProvider.string(R.string.waiting_for_server)))

        override fun connect(arg: Unit) = push(CloudConnection.Success)

        override suspend fun update(isConnected: Boolean,socket: Socket) {
            if (isConnected) {
                val serverState = connection.serverState(socket)
                serverState.update(this,resourceProvider)
            } else {
                push(CloudConnection.Failure(
                    resourceProvider.string(R.string.waiting_for_network)
                ))
            }
        }

        override fun push(state: CloudConnection) = block.invoke(state)

        override fun subscribe(block: (CloudConnection) -> Unit) {
            this.block = block
        }

    }
}