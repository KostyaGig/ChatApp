package ru.zinoview.viewmodelmemoryleak.ui.connection

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import ru.zinoview.viewmodelmemoryleak.data.connection.ConnectionRepository
import ru.zinoview.viewmodelmemoryleak.ui.chat.UpdateNetworkState
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher
import ru.zinoview.viewmodelmemoryleak.ui.core.ObserveConnection

interface UiConnectionWrapper : ObserveConnection, UpdateNetworkState<CoroutineScope> {

    fun connection(scope: CoroutineScope)

    class Base(
        private val dispatcher: Dispatcher,
        private val work: NetworkConnectionWork,
        private val repository: ConnectionRepository,
        private val communication: ConnectionCommunication,
        private val mapper: DataToUiConnectionMapper
    ) : UiConnectionWrapper {

        override fun connection(scope: CoroutineScope) {

            work.doBackground(scope) {
                repository.observe { data ->
                    val ui = data.map(mapper)
                    dispatcher.doUi(scope) {
                        communication.postValue(ui)
                    }
                }
            }
        }

        override fun observeConnection(owner: LifecycleOwner, observer: Observer<UiConnection>)
            = communication.observe(owner, observer)

        override fun updateNetworkState(isConnected: Boolean,scope: CoroutineScope)
            = work.doBackground(scope) {
                repository.updateNetworkConnection(isConnected)
            }
    }
}