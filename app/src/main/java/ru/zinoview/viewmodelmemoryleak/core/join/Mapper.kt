package ru.zinoview.viewmodelmemoryleak.core.join

import ru.zinoview.viewmodelmemoryleak.core.FailureMapper

interface Mapper<T> : FailureMapper.String<T> {

    fun map() : T
}