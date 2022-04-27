package ru.zinoview.viewmodelmemoryleak.data.connection

import ru.zinoview.viewmodelmemoryleak.data.connection.cloud.CloudConnection
import ru.zinoview.viewmodelmemoryleak.data.connection.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.core.SuspendObserve
import ru.zinoview.viewmodelmemoryleak.ui.core.NetworkConnection

interface ConnectionRepository<T> : SuspendObserve<DataConnection>, NetworkConnection<T> {

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

        override suspend fun connection(isConnected: Boolean)
            = cloudDataSource.connection(isConnected)
    }

    class Test(
        private val cloudDataSource: CloudDataSource<CloudConnection>,
        private val mapper: CloudToDataConnectionMapper
    ) : ConnectionRepository<DataConnection> {

        override suspend fun observe(block: (DataConnection) -> Unit) {

        }

        override suspend fun connection(isConnected: Boolean): DataConnection {
            val cloud = cloudDataSource.connection(isConnected)
            return cloud.map(mapper)
        }

    }
}