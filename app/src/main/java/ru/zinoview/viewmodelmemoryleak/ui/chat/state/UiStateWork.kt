package ru.zinoview.viewmodelmemoryleak.ui.chat.state

import kotlinx.coroutines.CoroutineScope
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher
import ru.zinoview.viewmodelmemoryleak.ui.core.Work

class UiStateWork(
    private val dispatcher: Dispatcher
) : Work.Abstract<UiStates,UiStates>(dispatcher) {


    override fun execute(
        scope: CoroutineScope,
        background: suspend () -> UiStates,
        ui: (UiStates) -> Unit
    ) {
        dispatcher.doBackground(scope) {
            val data = background.invoke()

            dispatcher.doUi(scope) {
                ui.invoke(data)
            }
        }

    }
}