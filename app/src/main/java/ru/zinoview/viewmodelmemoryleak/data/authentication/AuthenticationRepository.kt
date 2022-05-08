package ru.zinoview.viewmodelmemoryleak.data.authentication

import java.lang.Exception

interface AuthenticationRepository {

    fun auth() : DataAuth

    class Base(
        private val prefs: ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences<Int,Unit>
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
    }
}