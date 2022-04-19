package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.zinoview.viewmodelmemoryleak.ui.core.Communication

class ScrollCommunication : Communication<UiScroll> {

    private var observer: Observer<UiScroll>? = null
    private var isNotSent = true

    // todo rewrite the code

    override fun postValue(value: UiScroll) {
        if (isNotSent) {
            observer?.onChanged(value)
            isNotSent = false
        }

    }

    override fun observe(owner: LifecycleOwner, observer: Observer<UiScroll>) {
        Log.d("zinoviewk","SUBSCRIBE OBSERVE $isNotSent")
        this.observer = observer
    }

}