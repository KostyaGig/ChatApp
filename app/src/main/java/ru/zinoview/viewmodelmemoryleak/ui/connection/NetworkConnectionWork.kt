package ru.zinoview.viewmodelmemoryleak.ui.connection

import kotlinx.coroutines.CoroutineScope
import ru.zinoview.viewmodelmemoryleak.data.connection.DataConnection
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher
import ru.zinoview.viewmodelmemoryleak.ui.core.Ui
import ru.zinoview.viewmodelmemoryleak.ui.core.Work

interface NetworkConnectionWork : Work<DataConnection, UiConnection> {

    class Base(
        private val dispatcher: Dispatcher,
        private val mapper: DataToUiConnectionMapper
    ) : NetworkConnectionWork, Work.Abstract<DataConnection,UiConnection>(dispatcher) {

        override fun execute(
            scope: CoroutineScope,
            background: suspend () -> DataConnection,
            ui: (UiConnection) -> Unit
        ) {
            doBackground(scope) {
                val dataConnection = background.invoke()
                val uiConnection = dataConnection.map(mapper)
                dispatcher.doUi(scope) {
                    ui.invoke(uiConnection)
                }
            }
        }
    }
}