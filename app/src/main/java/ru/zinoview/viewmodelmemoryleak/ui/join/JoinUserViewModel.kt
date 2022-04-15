package ru.zinoview.viewmodelmemoryleak.ui.join

import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.data.join.JoinUserRepository
import ru.zinoview.viewmodelmemoryleak.ui.core.ActionViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.CommunicationObserve
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher

interface JoinUserViewModel : Clean, CommunicationObserve<UiJoin>, ActionViewModel<String> {


    class Base(
        private val repository: JoinUserRepository,
        private val dispatcher: Dispatcher,
        private val communication: JoinUserCommunication,
        private val mapper: DataToUiJoinMapper
    ) : BaseViewModel<UiJoin>(repository,communication), JoinUserViewModel {

        override fun doAction(nickname: String) {
            repository.join(nickname) { dataJoin ->
                dispatcher.doUi(viewModelScope) {
                    communication.postValue(
                        dataJoin.map(mapper)
                    )
                }
            }
        }
    }
}