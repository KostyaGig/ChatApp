package ru.zinoview.viewmodelmemoryleak.domain.connection

import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.data.connection.ConnectionRepository
import ru.zinoview.viewmodelmemoryleak.data.core.SuspendObserve
import ru.zinoview.viewmodelmemoryleak.ui.core.NetworkConnection

interface ConnectionInteractor : SuspendObserve<DomainConnection>, NetworkConnection<Unit> {

    class Base(
        private val repository: ConnectionRepository<Unit>,
        private val mapper: DataToDomainConnectionMapper,
    ) : ConnectionInteractor {


        override suspend fun observe(block: (DomainConnection) -> Unit) {
            repository.observe { cloudConnection ->
                val data = cloudConnection.map(mapper)
                block.invoke(data)
            }
        }

        override suspend fun connection(isConnected: Boolean) = repository.connection(isConnected)
    }
}