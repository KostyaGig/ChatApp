package ru.zinoview.viewmodelmemoryleak.core

interface Data<T> {

    suspend fun data() : T
}