package ru.zinoview.viewmodelmemoryleak.ui.core

import ru.zinoview.viewmodelmemoryleak.core.Update

interface Adapter<T> : Update<T> {

    class Empty<T> : Adapter<T> {
        override fun update(data: T) = Unit
    }
}