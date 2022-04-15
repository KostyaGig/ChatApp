package ru.zinoview.viewmodelmemoryleak.data.connection

import ru.zinoview.viewmodelmemoryleak.data.core.SuspendObserve
import ru.zinoview.viewmodelmemoryleak.ui.core.CheckNetworkConnection

interface ConnectionRepository : SuspendObserve<DataConnection>, CheckNetworkConnection {

    class Base(
            private val mapper: CloudToDataConnectionMapper,
            private val cloudDataSource: ru.zinoview.viewmodelmemoryleak.data.connection.cloud.CloudDataSource
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