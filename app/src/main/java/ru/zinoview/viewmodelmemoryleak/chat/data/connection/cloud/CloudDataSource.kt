package ru.zinoview.viewmodelmemoryleak.chat.data.connection.cloud

import android.util.Log
import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.chat.core.SuspendObserve
import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.AbstractCloudDataSource
import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.SocketConnection

interface CloudDataSource : AbstractCloudDataSource, SuspendObserve<CloudConnection> {

    class Base(
        private val socket: Socket,
        private val connection: SocketConnection,
    ) : CloudDataSource, AbstractCloudDataSource.Base(socket, connection){

        override suspend fun observe(block: (CloudConnection) -> Unit) {
            connection.connect(socket)

            socket.on(Socket.EVENT_CONNECT) {
                block.invoke(CloudConnection.Connect)
                Log.d("zinoviewk","connection event")
            }

            socket.on(Socket.EVENT_DISCONNECT) {
                block.invoke(CloudConnection.ToolBarDisconnect)
            }

            connection.handleActivityActivity(socket) {
                block.invoke(
                    CloudConnection.Disconnect
                )
            }

            connection.addSocketBranch(Socket.EVENT_DISCONNECT)
            connection.addSocketBranch(Socket.EVENT_CONNECT)
        }

    }
}