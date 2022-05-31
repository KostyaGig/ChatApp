package ru.zinoview.viewmodelmemoryleak.ui.join

interface Dropped<T> {

    fun dropped() : T
}