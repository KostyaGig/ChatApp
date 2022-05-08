package ru.zinoview.viewmodelmemoryleak.ui.connection

import ru.zinoview.viewmodelmemoryleak.ui.core.Communication

class ConnectionCommunication  : Communication.Base<UiConnection>() {

    private var lastValue: UiConnection = UiConnection.Empty

    override fun postValue(value: UiConnection) {

        if (value.same(lastValue).not()) {
            super.postValue(value)
            lastValue = value
        }

    }
}