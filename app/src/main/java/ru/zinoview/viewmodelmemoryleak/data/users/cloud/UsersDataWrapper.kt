package ru.zinoview.viewmodelmemoryleak.data.users.cloud

import ru.zinoview.viewmodelmemoryleak.data.core.cloud.CloudDataWrapper

class UsersDataWrapper(
    values: ArrayList<UsersValue>
) : CloudDataWrapper<CloudUser.Base, UsersValue>(values, CloudUser.Base.Empty)