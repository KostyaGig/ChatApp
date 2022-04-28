package ru.zinoview.viewmodelmemoryleak.data.chat.notification.cloud

import android.util.Log
import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.data.chat.user_status.UserStatusWrapper
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.AbstractCloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.SocketConnection
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Subscribe

interface CloudDataSource : Disconnect<Unit>, Subscribe<List<CloudMessageNotification>> {

    class Base(
        private val socket: Socket,
        private val connection: SocketConnection,
        private val userStatus: UserStatusWrapper
    ) : AbstractCloudDataSource.Base(socket, connection), CloudDataSource {

        override fun subscribe(block: (List<CloudMessageNotification>) -> Unit) {
            connection.connect(socket)

            socket.on(NOTIFICATION) { notifications ->
                Log.d("zinoviewk","online user ${userStatus.isOnline()} \n\n notification $notifications")

                //userStatus.show(notifications)
            }

            connection.addSocketBranch(NOTIFICATION)
        }

        private companion object {
            private const val NOTIFICATION = "notification"
        }
    }

    class Test : CloudDataSource {
        override fun disconnect(arg: Unit) {

        }

        override fun subscribe(block: (List<CloudMessageNotification>) -> Unit) {
        }

    }
}