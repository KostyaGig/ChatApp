package ru.zinoview.viewmodelmemoryleak.data.chat.user_status.cloud

import ru.zinoview.viewmodelmemoryleak.core.chat.user_status.Offline
import ru.zinoview.viewmodelmemoryleak.core.chat.user_status.Online
import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketData
import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketWrapper
import ru.zinoview.viewmodelmemoryleak.data.chat.user_status.FirebaseMessageNotification
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.AbstractCloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Json

interface CloudDataSource : Offline, Online {

    class Base(
        private val socketWrapper: SocketWrapper,
        private val firebase: FirebaseMessageNotification,
        private val json: Json
        ) : AbstractCloudDataSource.Base(socketWrapper),CloudDataSource {

        override suspend fun online() {
            val notificationTokenJson = SocketData.Base(json.json(
                Pair(NOTIFICATION_TOKEN,firebase.notificationToken())
            ))

            socketWrapper.emit(CONNECT,notificationTokenJson)
        }

        override suspend fun offline() {
            val notificationTokenJson = SocketData.Base(json.json(
                Pair(NOTIFICATION_TOKEN,firebase.notificationToken())
            ))

            socketWrapper.emit(DISCONNECT,notificationTokenJson)
        }

        private companion object {
            private const val CONNECT = "connect_user"
            private const val DISCONNECT = "disconnect_user"

            private const val NOTIFICATION_TOKEN = "notification_token"
        }

    }
}