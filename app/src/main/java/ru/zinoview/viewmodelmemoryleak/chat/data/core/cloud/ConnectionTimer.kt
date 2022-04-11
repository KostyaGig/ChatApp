package ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud

import android.os.CountDownTimer

abstract class ConnectionTimer(
    start: Long
) : CountDownTimer(start,INTERVAL) {

    private companion object {
        private const val INTERVAL = 1000L
    }

    override fun onTick(p0: Long) = Unit
}