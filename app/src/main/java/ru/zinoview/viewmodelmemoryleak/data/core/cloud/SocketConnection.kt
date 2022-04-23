package ru.zinoview.viewmodelmemoryleak.data.core.cloud

import android.util.Log
import io.socket.client.Socket


interface SocketConnection : Disconnect<Socket>,ServerState, Connect<Socket> {

    fun disconnectBranch(socket: Socket,branch: String)

    fun addSocketBranch(branch: String)

    class Base(
        private val activity: ActivityConnection
    ) : SocketConnection {

        private val branches = ArrayList<String>()

        override fun connect(socket: Socket) {
            if (activity.isNotActive(socket)) {
                socket.connect()
            }
        }

        override suspend fun serverState(socket: Socket) : CloudServerState
            = activity.serverState(socket)

        override fun disconnect(socket: Socket) {
            if (socket.isActive) {
                branches.forEach { branch ->
                    Log.d("zinoviewk","disconnect branch $branch")
                    disconnectBranch(socket,branch)
                }
            }
        }

        override fun disconnectBranch(socket: Socket, branch: String) {
            socket.off(branch)
        }

        override fun addSocketBranch(branch: String) {
            branches.add(branch)
        }

    }
}