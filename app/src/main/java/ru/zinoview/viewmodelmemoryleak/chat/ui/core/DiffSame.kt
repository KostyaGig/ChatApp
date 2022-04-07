package ru.zinoview.viewmodelmemoryleak.chat.ui.core

interface DiffSame<T> {

    fun isItemTheSame(item: T) : Boolean
    fun isContentTheSame(item: T) : Boolean

}