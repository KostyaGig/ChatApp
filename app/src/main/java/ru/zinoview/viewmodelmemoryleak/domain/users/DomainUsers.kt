package ru.zinoview.viewmodelmemoryleak.domain.users

import ru.zinoview.viewmodelmemoryleak.core.users.AbstractUser
import ru.zinoview.viewmodelmemoryleak.core.users.Mapper
import ru.zinoview.viewmodelmemoryleak.core.users.Users

sealed class DomainUsers : Users {

    data class Success(private val users: List<AbstractUser>) : DomainUsers() {
        override fun <T> map(mapper: Mapper<T>) = mapper.map(users)
    }

    data class Failure(
        private val message: String
    ) : DomainUsers() {
        override fun <T> map(mapper: Mapper<T>) = mapper.mapFailure(message)
    }
}