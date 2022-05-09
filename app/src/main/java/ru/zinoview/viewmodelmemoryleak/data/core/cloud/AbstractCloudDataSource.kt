package ru.zinoview.viewmodelmemoryleak.data.core.cloud

import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketWrapper

interface AbstractCloudDataSource : Disconnect<Unit> {

    abstract class Base(
        private val socket: SocketWrapper
    ) : Disconnect<Unit> {

        override fun disconnect(arg: Unit) = socket.disconnect("")
    }
}