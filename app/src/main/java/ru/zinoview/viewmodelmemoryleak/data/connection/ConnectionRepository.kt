package ru.zinoview.viewmodelmemoryleak.data.connection

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

        override suspend fun connection(isConnected: Boolean) =
            cloudDataSource.connection(isConnected)
    }
}