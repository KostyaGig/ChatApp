package ru.zinoview.viewmodelmemoryleak.data.core.ui_state

import ru.zinoview.viewmodelmemoryleak.core.Save

interface UiStateRepository<T,R> : Save<T> {

    fun read(key: Unit,map: (T) -> R): R

    abstract class Base<T,R>(
        private val prefs: UiStateSharedPreferences<T,R>
    ): UiStateRepository<T,R> {

        override fun save(state: T) = prefs.save(state)

        override fun read(key: Unit, map: (T) -> R)
            = prefs.read(key, map)
    }

}