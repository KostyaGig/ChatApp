package ru.zinoview.viewmodelmemoryleak.core.authentication

import ru.zinoview.viewmodelmemoryleak.core.FailureMapper

interface Mapper<T> : FailureMapper.Unit<T> {
    fun map() : T
}