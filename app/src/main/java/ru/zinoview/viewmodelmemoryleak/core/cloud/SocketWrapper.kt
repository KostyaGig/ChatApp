package ru.zinoview.viewmodelmemoryleak.core.cloud

import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Connect
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.IsNotActive
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.SocketConnection

interface SocketWrapper : Emit,Connect<String>, Subscribe,Disconnect<Unit>, IsNotActive {

    class Base(
        private val socket: Socket,
        private val connection: SocketConnection,
    ) : SocketWrapper {

        private val branches = ArrayList<String>()

        override fun emit(branch: String, data: SocketData) {
            connection.connect(socket)
            branches.add(branch)

            data.push(socket,branch)
        }

        override fun connect(branch: String) {
            connection.connect(socket)
            if (branch.isNotEmpty()) {
                branches.add(branch)
            }
        }

        override fun subscribe(branch: String, block: (Array<Any>) -> Unit) {
            socket.on(branch) { data ->
                if (data != null) {
                    block.invoke(data)
                }
            }
        }

        override fun disconnect(arg: Unit) {
            if (socket.isActive) {
                branches.forEach { branch ->
                    socket.off(branch)
                }
            }
        }

        override fun isNotActive(): Boolean {
            val isActive = socket.isActive
            val idServer = socket.id()
            return isActive.not() || idServer == null
        }

    }
}