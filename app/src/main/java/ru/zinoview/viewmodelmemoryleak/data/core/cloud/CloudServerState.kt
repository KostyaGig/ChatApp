package ru.zinoview.viewmodelmemoryleak.data.core.cloud

import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.data.connection.cloud.CloudConnection
import ru.zinoview.viewmodelmemoryleak.data.connection.cloud.ConnectionState

interface CloudServerState : Update<ConnectionState,ResourceProvider> {

    object Died : CloudServerState {

        override fun update(connectionState: ConnectionState,resourceProvider: ResourceProvider) {
            connectionState.push(
                CloudConnection.Failure(
                    resourceProvider.string(R.string.waiting_for_server)
                )
            )
        }
    }

    object Alive : CloudServerState {
        override fun update(connectionState: ConnectionState, resourceProvider: ResourceProvider) {
            connectionState.push(
                CloudConnection.Success
            )
        }

    }

}