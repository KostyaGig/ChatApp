package ru.zinoview.viewmodelmemoryleak.ui.core

interface UpdateNetworkConnection<T> {

    suspend fun updateNetworkConnection(isConnected: Boolean) : T
}