package ru.zinoview.viewmodelmemoryleak.ui.core

interface NetworkConnection<T>{

    suspend fun connection(isConnected: Boolean) : T
}