package ru.zinoview.viewmodelmemoryleak.ui.join

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.data.join.JoinUserRepository
import ru.zinoview.viewmodelmemoryleak.ui.connection.UiConnection
import ru.zinoview.viewmodelmemoryleak.ui.connection.UiConnectionWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.BaseViewModel

interface JoinUserViewModel :
        Clean, JoinUserViewModelObserve, ru.zinoview.viewmodelmemoryleak.ui.core.ConnectionViewModel {

    fun joinUser(image: ImageProfile, nickName: String)

    class Base(
        private val repository: JoinUserRepository,
        private val work: JoinWork,
        private val communication: JoinUserCommunication,
        private val connectionWrapper: UiConnectionWrapper
    ) : BaseViewModel<UiJoin>(listOf(repository),communication), JoinUserViewModel {

        override fun joinUser(image: ImageProfile,nickName: String) {
            work.execute(viewModelScope,{
                repository.joinedUserId(image,nickName)
            },{ uiJoin ->
                communication.postValue(uiJoin)
            })
        }

        override fun connection() = connectionWrapper.connection(viewModelScope)

        override fun observeConnection(owner: LifecycleOwner, observer: Observer<UiConnection>)
            = connectionWrapper.observeConnection(owner, observer)

        override fun updateNetworkState(isConnected: Boolean, arg: Unit)
            = work.doBackground(viewModelScope) {
                connectionWrapper.updateNetworkState(isConnected,viewModelScope)
        }
    }
}