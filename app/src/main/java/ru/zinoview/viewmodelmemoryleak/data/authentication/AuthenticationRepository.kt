package ru.zinoview.viewmodelmemoryleak.data.authentication

import java.lang.Exception

interface AuthenticationRepository : ru.zinoview.viewmodelmemoryleak.core.Clean {

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

        override fun clean() = Unit
    }

    class Test : AuthenticationRepository {

        private var isAuth = false

        override fun auth(): DataAuth {
            val result = if (isAuth) {
                DataAuth.Success
            } else {
                DataAuth.Failure
            }
            isAuth = isAuth.not()
            return result
        }

        override fun clean() = Unit
    }
}