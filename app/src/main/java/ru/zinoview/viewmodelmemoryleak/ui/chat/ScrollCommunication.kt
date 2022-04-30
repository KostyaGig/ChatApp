package ru.zinoview.viewmodelmemoryleak.ui.chat

import ru.zinoview.viewmodelmemoryleak.ui.core.Communication

class ScrollCommunication : Communication.Single<UiScroll>() {

    override fun postValue(value: UiScroll) {
        super.postValue(value)
//        if (liveData.hasActiveObservers()) {
//            super.postValue(value)
//        }
    }

}