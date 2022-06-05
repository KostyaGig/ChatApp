package ru.zinoview.viewmodelmemoryleak.ui.users

import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.domain.users.UsersInteractor
import ru.zinoview.viewmodelmemoryleak.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.CommunicationObserve
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher

interface UsersViewModel : CommunicationObserve<UiUsers> {

    fun users()

    class Base(
        private val interactor: UsersInteractor,
        private val dispatcher: Dispatcher,
        private val mapper: DomainToUiUserMapper,
        private val communication: UsersCommunication
    ) : UsersViewModel, BaseViewModel<UiUsers>(communication) {

        override fun users() {
            dispatcher.doUi(viewModelScope) {
                val domain = interactor.data()
                val ui = domain.map(mapper)

                dispatcher.doUi(viewModelScope) {
                    communication.postValue(ui)
                }
            }
        }

    }
}