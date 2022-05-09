package ru.zinoview.viewmodelmemoryleak.core.users

import ru.zinoview.viewmodelmemoryleak.core.FailureMapper

interface Mapper<T> : FailureMapper.String<T> {

    fun map(users: List<AbstractUser>) : T
}