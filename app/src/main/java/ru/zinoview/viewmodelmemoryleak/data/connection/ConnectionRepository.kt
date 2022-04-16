package ru.zinoview.viewmodelmemoryleak.data.connection

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.data.core.SuspendObserve
import ru.zinoview.viewmodelmemoryleak.ui.core.UpdateNetworkConnection

interface ConnectionRepository : SuspendObserve<DataConnection>, UpdateNetworkConnection {

    class Base(
            private val mapper: CloudToDataConnectionMapper,
            private val cloudDataSource: ru.zinoview.viewmodelmemoryleak.data.connection.cloud.CloudDataSource
        ) : ConnectionRepository {

        init {
            Log.d("zinoviewk","connection repo init")
        }

        override suspend fun observe(block: (DataConnection) -> Unit) {
            cloudDataSource.observe { cloudConnection ->
                val data = cloudConnection.map(mapper)
                block.invoke(data)
            }
        }

        override suspend fun updateNetworkConnection(isConnected: Boolean)
            = cloudDataSource.updateNetworkConnection(isConnected)
    }
}