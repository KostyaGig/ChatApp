package ru.zinoview.viewmodelmemoryleak.chat.ui.join

import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.join.JoinUserRepository
import ru.zinoview.viewmodelmemoryleak.chat.core.Clean
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.CommunicationObserve
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Dispatcher

interface JoinUserViewModel : Clean, CommunicationObserve<Unit> {

    fun join(nickname: String)

    class Base(
        private val repository: JoinUserRepository,
        private val dispatcher: Dispatcher,
        private val communication: JoinUserCommunication
    ) : BaseViewModel<Unit>(repository,communication), JoinUserViewModel {

        override fun join(nickname: String) {
            dispatcher.doBackground(viewModelScope) {
                repository.join(nickname) {
                    communication.postValue(Unit)
                }
            }
        }
    }
}