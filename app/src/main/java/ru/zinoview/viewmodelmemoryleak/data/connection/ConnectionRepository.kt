package ru.zinoview.viewmodelmemoryleak.data.connection

import ru.zinoview.viewmodelmemoryleak.data.connection.cloud.CloudConnection
import ru.zinoview.viewmodelmemoryleak.data.connection.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.core.SuspendObserve
import ru.zinoview.viewmodelmemoryleak.ui.core.UpdateNetworkConnection

interface ConnectionRepository<T> : SuspendObserve<DataConnection>, UpdateNetworkConnection<T> {

    class Base(
        private val mapper: CloudToDataConnectionMapper,
        private val cloudDataSource: CloudDataSource<Unit>
        ) : ConnectionRepository<Unit> {


        override suspend fun observe(block: (DataConnection) -> Unit) {
            cloudDataSource.observe { cloudConnection ->
                val data = cloudConnection.map(mapper)
                block.invoke(data)
            }
        }

        override suspend fun updateNetworkConnection(isConnected: Boolean)
            = cloudDataSource.updateNetworkConnection(isConnected)
    }

    class Test(
        private val cloudDataSource: CloudDataSource<CloudConnection>,
        private val mapper: CloudToDataConnectionMapper
    ) : ConnectionRepository<DataConnection> {

        override suspend fun observe(block: (DataConnection) -> Unit) {

        }

        override suspend fun updateNetworkConnection(isConnected: Boolean): DataConnection {
            val cloud = cloudDataSource.updateNetworkConnection(isConnected)
            return cloud.map(mapper)
        }

    }
}