package ru.zinoview.viewmodelmemoryleak.chat.data.connection.cloud

import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.SocketConnection
import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.Subscribe

interface ConnectionState : Subscribe<CloudConnection> {

    fun change(state: Boolean,message: String = "")

    class Base(
        private val socket: Socket,
        private val connection: SocketConnection
    ) : ConnectionState {

        private var block: (CloudConnection) -> Unit = {}

        override fun change(state: Boolean, message: String) {
            if (!state) {
                block.invoke(CloudConnection.Failure(message))
            } else {
                connection.handleActivityConnection(socket) {
                    block.invoke(CloudConnection.WaitingForServer())
                }
            }
        }

        override fun subscribe(block: (CloudConnection) -> Unit) {
            this.block = block
        }

    }
}