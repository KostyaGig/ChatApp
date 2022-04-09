package ru.zinoview.viewmodelmemoryleak.chat.data.connection

import ru.zinoview.viewmodelmemoryleak.chat.core.SuspendObserve

interface ConnectionRepository : SuspendObserve<DataConnection> {

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

    }
}