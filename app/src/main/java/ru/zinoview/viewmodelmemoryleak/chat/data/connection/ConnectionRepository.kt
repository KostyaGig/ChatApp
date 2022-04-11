package ru.zinoview.viewmodelmemoryleak.chat.data.connection

import ru.zinoview.viewmodelmemoryleak.chat.core.SuspendObserve
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.CheckNetworkConnection

interface ConnectionRepository : SuspendObserve<DataConnection>, CheckNetworkConnection {

    class Base(
            private val mapper: CloudToDataConnectionMapper,
            private val cloudDataSource: ru.zinoview.viewmodelmemoryleak.chat.data.connection.cloud.CloudDataSource
        ) : ConnectionRepository {

        override suspend fun observe(block: (DataConnection) -> Unit) {
            cloudDataSource.observe { cloudConnection ->
                val data = cloudConnection.map(mapper)
                block.invoke(data)
            }
        }

        override fun checkNetworkConnection(state: Boolean)
            = cloudDataSource.checkNetworkConnection(state)
    }
}