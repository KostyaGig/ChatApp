package ru.zinoview.viewmodelmemoryleak.ui.users

import ru.zinoview.viewmodelmemoryleak.core.users.UserBitmap
import ru.zinoview.viewmodelmemoryleak.core.users.UserMapper

interface UiToBundleUserMapper : UserMapper<BundleUser> {


    class Base : UiToBundleUserMapper {

        override fun map(
            userId: String,
            nickName: String,
            lastMessageText: String,
            image: UserBitmap
        ) = BundleUser.Base(userId,nickName)
    }
}