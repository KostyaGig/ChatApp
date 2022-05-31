package ru.zinoview.viewmodelmemoryleak.ui.core.ui_state

import kotlinx.coroutines.CoroutineScope
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher
import ru.zinoview.viewmodelmemoryleak.ui.core.Work

class UiStateWork<T>(
    private val dispatcher: Dispatcher
) : Work.Abstract<T,T>(dispatcher) {

    override fun execute(
        scope: CoroutineScope,
        background: suspend () -> T,
        ui: (T) -> Unit
    ) {
        dispatcher.doBackground(scope) {
            val data = background.invoke()

            dispatcher.doUi(scope) {
                ui.invoke(data)
            }
        }

    }
}