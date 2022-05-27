package ru.zinoview.viewmodelmemoryleak.ui.join

import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.data.join.JoinUserRepository
import ru.zinoview.viewmodelmemoryleak.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.CommunicationObserve
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher

interface JoinUserViewModel : Clean, CommunicationObserve<UiJoin> {

    fun joinUser(image: ImageProfile, nickName: String)

    class Base(
        private val repository: JoinUserRepository,
        private val dispatcher: Dispatcher,
        private val communication: JoinUserCommunication,
        private val mapper: DataToUiJoinMapper
    ) : BaseViewModel<UiJoin>(communication,listOf(repository,communication)), JoinUserViewModel {

        override fun joinUser(image: ImageProfile,nickName: String) {

            dispatcher.doBackground(viewModelScope) {
                repository.joinedUserId(image, nickName) { dataJoin ->
                    val uiJoin = dataJoin.map(mapper)
                    dispatcher.doUi(viewModelScope) {
                        communication.postValue(uiJoin)
                    }
                }
            }
        }
    }

}