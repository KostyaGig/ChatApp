package ru.zinoview.viewmodelmemoryleak.chat.ui.connection

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import ru.zinoview.viewmodelmemoryleak.chat.data.connection.ConnectionRepository
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.CheckNetworkConnection
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Dispatcher
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.ObserveConnection

interface UiConnectionWrapper : ObserveConnection, CheckNetworkConnection {

    fun connection(scope: CoroutineScope)

    class Base(
        private val dispatcher: Dispatcher,
        private val connectionRepository: ConnectionRepository,
        private val connectionCommunication: ConnectionCommunication,
        private val connectionMapper: DataToUiConnectionMapper
    ) : UiConnectionWrapper {

        override fun connection(scope: CoroutineScope) {
            dispatcher.doBackground(scope) {
                connectionRepository.observe { data ->
                    val ui = data.map(connectionMapper)
                    dispatcher.doUi(scope) {
                        connectionCommunication.postValue(ui)
                    }
                }
            }
        }

        override fun observeConnection(owner: LifecycleOwner, observer: Observer<UiConnection>)
            = connectionCommunication.observe(owner, observer)

        override fun checkNetworkConnection(state: Boolean)
            = connectionRepository.checkNetworkConnection(state)
    }
}