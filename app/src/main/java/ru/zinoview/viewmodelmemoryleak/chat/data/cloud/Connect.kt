package ru.zinoview.viewmodelmemoryleak.chat.data.cloud

import android.util.Log
import io.socket.client.Socket


interface Connect {

    fun connect(socket: Socket)

    fun disconnect(socket: Socket)

    fun disconnectBranch(socket: Socket,branch: String)

    fun addSocketBranch(branch: String)

    class Base : Connect {

        private val branches = ArrayList<String>()

        override fun connect(socket: Socket) {
            if (socket.isActive.not()) {
                socket.connect()
            } else {
                Log.d("zinoviewk","socket is connect")
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