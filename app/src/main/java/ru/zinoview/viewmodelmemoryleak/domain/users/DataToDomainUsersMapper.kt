package ru.zinoview.viewmodelmemoryleak.domain.users

import ru.zinoview.viewmodelmemoryleak.core.users.AbstractUser
import ru.zinoview.viewmodelmemoryleak.core.users.Mapper

interface DataToDomainUsersMapper : Mapper<DomainUsers> {

    class Base : DataToDomainUsersMapper {
        override fun map(users: List<AbstractUser>)
            = DomainUsers.Success(users)

        override fun mapFailure(message: String)
            = DomainUsers.Failure(message)
    }
}