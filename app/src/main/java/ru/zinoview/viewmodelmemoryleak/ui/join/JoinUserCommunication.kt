package ru.zinoview.viewmodelmemoryleak.ui.join

import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.ui.core.Communication

class JoinUserCommunication : Communication.Base<UiJoin>(), Clean {

    override fun clean() = postValue(UiJoin.Empty)
}