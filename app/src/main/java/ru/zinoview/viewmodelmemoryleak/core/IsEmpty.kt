package ru.zinoview.viewmodelmemoryleak.core

interface IsEmpty<T> {

    fun isEmpty(arg: T) : Boolean
}