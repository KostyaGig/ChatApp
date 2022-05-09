package ru.zinoview.viewmodelmemoryleak.domain.users

import ru.zinoview.viewmodelmemoryleak.core.Data
import ru.zinoview.viewmodelmemoryleak.data.users.UsersRepository

interface UsersInteractor : Data<DomainUsers> {

    class Base(
        private val repository: UsersRepository,
        private val mapper: DataToDomainUsersMapper,
        ) : UsersInteractor {


        override suspend fun data()
            = repository.data().map(mapper)
    }
}