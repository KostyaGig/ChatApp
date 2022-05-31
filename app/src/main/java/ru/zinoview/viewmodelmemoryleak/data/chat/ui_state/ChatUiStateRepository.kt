package ru.zinoview.viewmodelmemoryleak.data.chat.ui_state

import ru.zinoview.viewmodelmemoryleak.data.chat.DataMessage
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudToDataMessageMapper
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.MessagesStore
import ru.zinoview.viewmodelmemoryleak.data.core.ui_state.UiStateRepository
import ru.zinoview.viewmodelmemoryleak.data.core.ui_state.UiStateSharedPreferences
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.ChatUiStates
import ru.zinoview.viewmodelmemoryleak.core.chat.ui_state.Messages

interface ChatUiStateRepository : UiStateRepository<ChatUiStates.Base,ChatUiStates>, Messages<DataMessage> {

    class Base(
        prefs: UiStateSharedPreferences<ChatUiStates.Base,ChatUiStates>,
        private val messagesStore: MessagesStore,
        private val mapper: CloudToDataMessageMapper
    ) : ChatUiStateRepository, UiStateRepository.Base<ChatUiStates.Base,ChatUiStates>(prefs) {

        override fun messages(): List<DataMessage> {
            val cloudMessages = messagesStore.messages()
            return cloudMessages.map { it.map(mapper) }
        }
    }

}