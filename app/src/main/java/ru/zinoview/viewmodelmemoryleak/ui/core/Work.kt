package ru.zinoview.viewmodelmemoryleak.ui.core

import kotlinx.coroutines.*

interface Work<T,U> : DoBackground {
    fun execute(scope: CoroutineScope,background: suspend () -> T, ui:(U) -> Unit)

    abstract class Abstract<T,U>(
        private val dispatcher: Dispatcher
    ) : Work<T,U> {

        override fun doBackground(scope: CoroutineScope, block: suspend () -> Unit)
            = dispatcher.doBackground(scope,block)
    }
}
