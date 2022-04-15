package ru.zinoview.viewmodelmemoryleak.data.connection.cloud

import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.SocketConnection
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Subscribe

interface ConnectionState : Subscribe<CloudConnection> {

    fun change(state: Boolean,message: String = "")

    class Base(
        private val socket: Socket,
        private val connection: SocketConnection,
        private val resourceProvider: ResourceProvider
    ) : ConnectionState {

        private var block: (CloudConnection) -> Unit = {}

        override fun change(state: Boolean, message: String) {
            if (!state) {
                block.invoke(CloudConnection.Failure(message))
            } else {
                connection.handleActivityConnection(socket) {
                    block.invoke(
                        CloudConnection.Failure(
                            resourceProvider.string(R.string.waiting_for_server)
                        ))
                }
            }
        }

        override fun subscribe(block: (CloudConnection) -> Unit) {
            this.block = block
        }

    }
}