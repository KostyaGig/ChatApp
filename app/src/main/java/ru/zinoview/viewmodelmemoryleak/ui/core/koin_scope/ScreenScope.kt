package ru.zinoview.viewmodelmemoryleak.ui.core.koin_scope

import org.koin.core.KoinContext

interface ScreenScope {

    fun scope(koinContext: KoinContext)

    fun clean(koinContext: KoinContext)

    abstract class BaseScreenScope(
        private val scopeName: String
    ) : ScreenScope {

        override fun scope(koinContext: KoinContext) {
            koinContext.getOrCreateScope(scopeName)
        }

        override fun clean(koinContext: KoinContext)
            = koinContext.getOrCreateScope(scopeName).close()
    }

    class Connection : BaseScreenScope(SCOPE) {

        private companion object {
            private const val SCOPE = "connectionScope"
        }
    }

    class Chat : BaseScreenScope(SCOPE) {

        private companion object {
            private const val SCOPE = "chatScope"
        }
    }

    class Join : BaseScreenScope(SCOPE) {

        private companion object {
            private const val SCOPE = "joinScope"
        }
    }

    class Users : BaseScreenScope(SCOPE) {

        private companion object {
            private const val SCOPE = "usersScope"
        }
    }

    class Auth : BaseScreenScope(SCOPE) {

        private companion object {
            private const val SCOPE = "authScope"
        }
    }
}
