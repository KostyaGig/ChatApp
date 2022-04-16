package ru.zinoview.viewmodelmemoryleak.ui.core

import kotlinx.coroutines.CoroutineScope

interface DoBackground {

    fun doBackground(scope: CoroutineScope, block: suspend () -> Unit)
}