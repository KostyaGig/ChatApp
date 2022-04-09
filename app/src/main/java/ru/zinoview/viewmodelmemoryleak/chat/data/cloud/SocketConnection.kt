package ru.zinoview.viewmodelmemoryleak.chat.data.cloud

import io.socket.client.Socket


interface SocketConnection : Disconnect<Socket> {

    fun connect(socket: Socket)

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

        override fun disconnect(socket: Socket) {
            if (socket.isActive) {
                socket.disconnect()
                branches.forEach { branch ->
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