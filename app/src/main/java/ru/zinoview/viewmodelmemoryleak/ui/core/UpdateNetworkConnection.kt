package ru.zinoview.viewmodelmemoryleak.ui.core

interface UpdateNetworkConnection {

    suspend fun updateNetworkConnection(isConnected: Boolean)
}