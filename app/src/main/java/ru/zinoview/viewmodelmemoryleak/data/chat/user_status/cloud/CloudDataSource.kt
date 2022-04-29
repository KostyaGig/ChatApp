package ru.zinoview.viewmodelmemoryleak.data.chat.user_status.cloud

import ru.zinoview.viewmodelmemoryleak.core.chat.user_status.Offline
import ru.zinoview.viewmodelmemoryleak.core.chat.user_status.Online
import ru.zinoview.viewmodelmemoryleak.data.chat.user_status.FirebaseMessageNotification
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.AbstractCloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Json
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.SocketConnection

interface CloudDataSource : Offline, Online {

    class Base(
        private val socket: io.socket.client.Socket,
        private val connection: SocketConnection,
        private val firebase: FirebaseMessageNotification,
        private val json: Json
        ) : AbstractCloudDataSource.Base(socket, connection),CloudDataSource {

        override suspend fun online() {
            connection.connect(socket)

            val notificationTokenJson = json.json(
                Pair(NOTIFICATION_TOKEN,firebase.notificationToken())
            )

            socket.emit(CONNECT,notificationTokenJson)
            connection.addSocketBranch(CONNECT)
        }

        override suspend fun offline() {
            connection.connect(socket)

            val notificationTokenJson = json.json(
                Pair(NOTIFICATION_TOKEN,firebase.notificationToken())
            )

            socket.emit(DISCONNECT,notificationTokenJson)
            connection.addSocketBranch(DISCONNECT)
        }

        private companion object {
            private const val CONNECT = "connect_user"
            private const val DISCONNECT = "disconnect_user"

            private const val NOTIFICATION_TOKEN = "notification_token"
        }

    }
}