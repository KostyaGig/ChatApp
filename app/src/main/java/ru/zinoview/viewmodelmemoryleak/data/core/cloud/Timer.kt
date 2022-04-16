package ru.zinoview.viewmodelmemoryleak.data.core.cloud

import android.util.Log
import java.util.concurrent.Executors

interface Timer {

    fun start(timeInMillis: Long,onFinish:() -> Unit)

    class Base : Timer {
        private val executorService = Executors.newFixedThreadPool(THREAD_COUNT)

        override fun start(timeInMillis: Long, onFinish: () -> Unit) {
            executorService.submit {
                Thread.sleep(timeInMillis)
                onFinish.invoke()
            }
        }

        private companion object {
            private const val THREAD_COUNT = 1
        }
    }
}