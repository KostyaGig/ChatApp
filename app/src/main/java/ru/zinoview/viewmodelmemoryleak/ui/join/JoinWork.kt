package ru.zinoview.viewmodelmemoryleak.ui.join

import kotlinx.coroutines.CoroutineScope
import ru.zinoview.viewmodelmemoryleak.data.join.DataJoin
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher
import ru.zinoview.viewmodelmemoryleak.ui.core.Work

interface JoinWork : Work<DataJoin,UiJoin> {

    class Base(
        private val dispatcher: Dispatcher,
        private val mapper: DataToUiJoinMapper
    ) : JoinWork,Work.Abstract<DataJoin,UiJoin>(dispatcher) {

        override fun execute(
            scope: CoroutineScope,
            background: suspend () -> DataJoin,
            ui: (UiJoin) -> Unit
        ) {
            dispatcher.doBackground(scope) {
                val dataJoin = background.invoke()
                val uiJoin = dataJoin.map(mapper)
                dispatcher.doUi(scope) {
                    ui.invoke(uiJoin)
                }
            }
        }


    }
}