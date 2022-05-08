package ru.zinoview.viewmodelmemoryleak.ui.join

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.data.join.JoinUserRepository
import ru.zinoview.viewmodelmemoryleak.ui.connection.UiConnection
import ru.zinoview.viewmodelmemoryleak.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.CommunicationObserve

interface JoinUserViewModel : Clean, CommunicationObserve<UiJoin> {

    fun joinUser(image: ImageProfile, nickName: String)

    class Base(
        private val repository: JoinUserRepository,
        private val work: JoinWork,
        private val communication: JoinUserCommunication
    ) : BaseViewModel<UiJoin>(communication,listOf(repository)), JoinUserViewModel {

        override fun joinUser(image: ImageProfile,nickName: String) {
            work.execute(viewModelScope,{
                repository.joinedUserId(image,nickName)
            },{ uiJoin ->
                communication.postValue(uiJoin)
            })
        }
    }
}