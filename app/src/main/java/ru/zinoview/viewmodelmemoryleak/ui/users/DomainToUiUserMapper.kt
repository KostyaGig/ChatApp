package ru.zinoview.viewmodelmemoryleak.ui.users

import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.core.users.AbstractUser
import ru.zinoview.viewmodelmemoryleak.core.users.Mapper

interface DomainToUiUserMapper : Mapper<UiUsers> {

    class Base(
        private val mapper: AbstractToUiUserMapper,
        private val resourceProvider: ResourceProvider
    ) : DomainToUiUserMapper {

        override fun map(users: List<AbstractUser>): UiUsers {
            val uiUsers = users.map { user -> user.map(mapper) }
            val toolbarTitle = resourceProvider.string(R.string.users_text) + uiUsers.size

            return UiUsers.Success(uiUsers,toolbarTitle)
        }


        override fun mapFailure(message: String)
            = UiUsers.Failure(message)
    }
}