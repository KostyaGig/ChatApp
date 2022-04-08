package ru.zinoview.viewmodelmemoryleak.chat.data.authentication

import ru.zinoview.viewmodelmemoryleak.chat.core.Clean
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences
import java.lang.Exception

interface AuthenticationRepository : Clean {

    fun auth() : DataAuth

    class Base(
        private val prefs: IdSharedPreferences
    ) : AuthenticationRepository {

        override fun auth(): DataAuth {
            return try {
                if (prefs.isEmpty()) {
                    DataAuth.Failure
                } else {
                    DataAuth.Success
                }
            } catch (e: Exception) {
                DataAuth.Failure
            }

        }

        override fun clean() = Unit
    }
}