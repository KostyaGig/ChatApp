package ru.zinoview.viewmodelmemoryleak.data.users

import ru.zinoview.viewmodelmemoryleak.core.users.AbstractUser
import ru.zinoview.viewmodelmemoryleak.core.users.Mapper
import ru.zinoview.viewmodelmemoryleak.core.users.Users

sealed class DataUsers : Users {

    data class Success(private val users: List<AbstractUser>) : DataUsers() {
        override fun <T> map(mapper: Mapper<T>) = mapper.map(users)
    }

    data class Failure(
        private val message: String
    ) : DataUsers() {

        override fun <T> map(mapper: Mapper<T>) =  mapper.mapFailure(message)
    }

    data class TestSuccess(
        private val users: List<AbstractUser>,
        private val toolbarTitle: String
    ) : DataUsers() {
        override fun <T> map(mapper: Mapper<T>) = mapper.map(users)
    }
}