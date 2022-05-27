package ru.zinoview.viewmodelmemoryleak.ui.connection

import android.util.Log
import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.domain.connection.ConnectionInteractor
import ru.zinoview.viewmodelmemoryleak.ui.chat.UpdateNetworkState
import ru.zinoview.viewmodelmemoryleak.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.CommunicationObserve
import ru.zinoview.viewmodelmemoryleak.ui.core.Connection
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher

interface ConnectionViewModel : CommunicationObserve<UiConnection>,Connection,UpdateNetworkState<Unit>  {

    class Base(
        private val dispatcher: Dispatcher,
        private val work: NetworkConnectionWork,
        private val interactor: ConnectionInteractor,
        private val communication: ConnectionCommunication,
        private val mapper: DataToUiConnectionMapper
    ) : BaseViewModel<UiConnection>(communication, listOf(communication)), ConnectionViewModel {

        init {
            Log.d("zinoviewk","ConnectionViewModel -> Init")
        }

        override fun connection() {
            work.doBackground(viewModelScope) {
                interactor.observe { data ->
                    val ui = data.map(mapper)
                    dispatcher.doUi(viewModelScope) {
                        communication.postValue(ui)
                    }
                }
            }
        }

        override fun updateNetworkState(isConnected: Boolean, arg: Unit)
            = work.doBackground(viewModelScope) {
                interactor.connection(isConnected)
            }
    }
}