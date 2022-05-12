package ru.zinoview.viewmodelmemoryleak.ui.core.koin_scope

import org.koin.core.KoinContext

interface KoinScope {

    fun scope(scope: ScreenScope,context: KoinContext)

    fun clean(context: KoinContext)

    class Base : KoinScope {

        private val scopeScreens = ArrayList<ScreenScope>()

        override fun scope(scope: ScreenScope, context: KoinContext) {
            scopeScreens.add(scope)
            scope.scope(context)
        }


        override fun clean(context: KoinContext) {
            scopeScreens.forEach { scope -> scope.clean(context) }
        }
    }
}