package ru.zinoview.viewmodelmemoryleak.ui.users

import android.graphics.Bitmap
import ru.zinoview.viewmodelmemoryleak.core.users.UserMapper
import ru.zinoview.viewmodelmemoryleak.ui.core.UiText

interface AbstractToUiUserMapper : UserMapper<UiUser> {

    class Base(
        private val uiText: UiText
    ) : AbstractToUiUserMapper {
        override fun map(userId: String, nickName: String, lastMessageText: String,image: Bitmap)
            = UiUser.Base(userId,nickName,uiText.map(lastMessageText),image)
    }
}