package ru.zinoview.viewmodelmemoryleak.data.authentication

import java.lang.Exception

interface AuthenticationRepository : ru.zinoview.viewmodelmemoryleak.core.Clean {

    fun auth() : DataAuth

    class Base(
        private val prefs: ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences
    ) : AuthenticationRepository {

        override fun auth(): DataAuth {
            return try {
                if (prefs.isEmpty(Unit)) {
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