package ru.zinoview.viewmodelmemoryleak.ui.connection

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.ui.core.Communication

class ConnectionCommunication  : Communication.Base<UiConnection>(), Clean {

    private var lastValue: UiConnection = UiConnection.Empty

    init {
        Log.d("zinoviewk","COMMUNICATION INIT")
    }

    override fun postValue(value: UiConnection) {
        if (value.same(lastValue).not()) {
            super.postValue(value)
            lastValue = value
        }
    }

    override fun clean() {
        lastValue = UiConnection.Empty
        super.postValue(lastValue)
    }
}