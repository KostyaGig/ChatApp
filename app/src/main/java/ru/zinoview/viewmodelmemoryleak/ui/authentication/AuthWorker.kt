package ru.zinoview.viewmodelmemoryleak.ui.authentication

import kotlinx.coroutines.CoroutineScope
import ru.zinoview.viewmodelmemoryleak.data.authentication.DataAuth
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher
import ru.zinoview.viewmodelmemoryleak.ui.core.Work

interface AuthWorker : Work<DataAuth,UiAuth> {

    class Base(
        private val dispatcher: Dispatcher,
        private val mapper: DataToUiAuthMapper
    ) : AuthWorker,Work.Abstract<DataAuth,UiAuth>(dispatcher) {

        override fun execute(
            scope: CoroutineScope,
            background: suspend () -> DataAuth,
            ui: (UiAuth) -> Unit
        ) {

            dispatcher.doBackground(scope) {
                val dataAuth = background.invoke()
                val uiAuth = dataAuth.map(mapper)
                dispatcher.doUi(scope) {
                    ui.invoke(uiAuth)
                }
            }

        }
    }
}