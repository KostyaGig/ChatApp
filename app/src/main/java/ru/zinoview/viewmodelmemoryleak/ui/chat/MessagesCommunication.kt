package ru.zinoview.viewmodelmemoryleak.ui.chat

import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.ui.core.Communication

class MessagesCommunication : Communication.StartValue<List<UiMessage>>(listOf(UiMessage.Progress)), Clean {

    override fun clean() = super.postValue(listOf(UiMessage.Progress))
}