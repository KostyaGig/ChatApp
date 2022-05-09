package ru.zinoview.viewmodelmemoryleak.core.users

interface Users {

    fun <T> map(mapper: Mapper<T>) : T
}