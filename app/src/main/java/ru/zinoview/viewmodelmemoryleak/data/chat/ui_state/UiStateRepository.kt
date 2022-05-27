package ru.zinoview.viewmodelmemoryleak.data.chat.ui_state

import ru.zinoview.viewmodelmemoryleak.core.Read
import ru.zinoview.viewmodelmemoryleak.core.Save
import ru.zinoview.viewmodelmemoryleak.core.chat.ui_state.Messages
import ru.zinoview.viewmodelmemoryleak.data.chat.DataMessage
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudToDataMessageMapper
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.MessagesStore
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStates
import ru.zinoview.viewmodelmemoryleak.ui.join.ui_state.JoinUiStates

interface UiStateRepository<T> : Save<T>, Read<T,Unit>, Messages<DataMessage> {

    override fun messages(): List<DataMessage> = emptyList()

    class Chat(
        private val prefs: UiStateSharedPreferences<UiStates.Base>,
        private val messagesStore: MessagesStore,
        private val mapper: CloudToDataMessageMapper
    ) : UiStateRepository<UiStates.Base> {

        override fun save(state: UiStates.Base) = prefs.save(state)

        override fun read(key: Unit) =  prefs.read(Unit)

        override fun messages(): List<DataMessage> {
            val cloudMessages = messagesStore.messages()
            return cloudMessages.map { it.map(mapper) }
        }
    }

    class Join(
        private val prefs: UiStateSharedPreferences<JoinUiStates.Base>,
    ) : UiStateRepository<JoinUiStates.Base> {

        override fun save(state: JoinUiStates.Base) = prefs.save(state)
        override fun read(key: Unit) =  prefs.read(Unit)
    }

}