package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.Time

interface TypeMessageTimer {

    fun startTyping()

    suspend fun stopTyping()

    class Base(
        private val viewModel: ChatViewModel.Base,
        private val time: Time<Long>
    ) : TypeMessageTimer {

        private var timeOfTimer = 0L

        override fun startTyping() {
            Log.d("zinoviewk2","start time ${time.time()}")
            timeOfTimer = time.time()
            viewModel.toTypeMessage(true)
        }

        override suspend fun stopTyping() {
            val currentTime = time.time()
            val difference = currentTime - timeOfTimer

            Log.d("zinoviewk2","stop typing,difference $difference")

            if (difference >= MAX_TIME_AFTER_STOP_TYPING) {
                viewModel.toTypeMessage(false)
            }
        }

        private companion object {
            private const val MAX_TIME_AFTER_STOP_TYPING = 500
        }
    }
}