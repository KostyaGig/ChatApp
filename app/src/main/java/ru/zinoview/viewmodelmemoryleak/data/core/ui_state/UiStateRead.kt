package ru.zinoview.viewmodelmemoryleak.data.core.ui_state

interface UiStateRead<T,R> {

    fun read(key: Unit,map: (T) -> R ): R
}