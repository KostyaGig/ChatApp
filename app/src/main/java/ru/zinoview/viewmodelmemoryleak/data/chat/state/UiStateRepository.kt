package ru.zinoview.viewmodelmemoryleak.data.chat.state

import ru.zinoview.viewmodelmemoryleak.core.Read
import ru.zinoview.viewmodelmemoryleak.core.Save
import ru.zinoview.viewmodelmemoryleak.core.chat.state.Messages
import ru.zinoview.viewmodelmemoryleak.data.chat.DataMessage
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudToDataMessageMapper
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.MessagesStore
import ru.zinoview.viewmodelmemoryleak.ui.chat.state.UiStates

interface UiStateRepository : Save<UiStates>, Read<UiStates,Unit>, Messages<DataMessage> {

    class Base(
        private val prefs: UiStateSharedPreferences,
        private val messagesStore: MessagesStore,
        private val mapper: CloudToDataMessageMapper
    ) : UiStateRepository {

        override fun save(state: UiStates) = prefs.save(state)

        override fun read(key: Unit) =  prefs.read(Unit)

        override fun messages(): List<DataMessage> {
            val cloudMessages = messagesStore.messages()
            return cloudMessages.map { it.map(mapper) }
        }
    }

    class Test : UiStateRepository {

        private var uiState: UiStates = UiStates.Test.Empty

        override fun save(state: UiStates) {
            uiState = state
        }

        override fun read(key: Unit) = (uiState as UiStates.Test).map()

        override fun messages(): List<DataMessage> = emptyList()
    }
}