package ru.zinoview.viewmodelmemoryleak.ui.chat

import kotlinx.coroutines.CoroutineScope
import ru.zinoview.viewmodelmemoryleak.data.chat.DataMessage
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher
import ru.zinoview.viewmodelmemoryleak.ui.core.Work

interface ChatWork : Work<List<DataMessage>,List<UiMessage>> {

    class Base(
        private val dispatcher: Dispatcher,
        private val mapper: DomainToUiMessageMapper
        ) : ChatWork, Work.Abstract<List<DataMessage>,List<UiMessage>>(dispatcher) {

        override fun execute(
            scope: CoroutineScope,
            background: suspend () -> List<DataMessage>,
            ui: (List<UiMessage>) -> Unit
        ) {
            doBackground(scope) {
                val dataMessages = background.invoke()
                val uiMessages = dataMessages.map { message -> message.map(mapper) }
                dispatcher.doUi(scope) {
                    ui.invoke(uiMessages)
                }
            }
        }


    }
}