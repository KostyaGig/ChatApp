package ru.zinoview.viewmodelmemoryleak.ui.users

import android.graphics.Bitmap
import ru.zinoview.viewmodelmemoryleak.core.users.UserMapper

interface AbstractToUiUserMapper : UserMapper<UiUser> {

    class Base : AbstractToUiUserMapper {
        override fun map(userId: String, nickName: String, image: Bitmap)
            = UiUser.Base(userId,nickName,image)
    }
}