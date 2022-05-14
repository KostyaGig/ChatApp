package ru.zinoview.viewmodelmemoryleak.ui.users

import ru.zinoview.viewmodelmemoryleak.core.users.UserBitmap
import ru.zinoview.viewmodelmemoryleak.core.users.UserMapper
import ru.zinoview.viewmodelmemoryleak.ui.core.UiText

interface AbstractToUiUserMapper : UserMapper<UiUser> {

    class Base(
        private val uiText: UiText
    ) : AbstractToUiUserMapper {

        override fun map(userId: String, nickName: String, lastMessageText: String,image: UserBitmap)
            = UiUser.Base(userId,nickName,uiText.map(lastMessageText),image)
    }
}