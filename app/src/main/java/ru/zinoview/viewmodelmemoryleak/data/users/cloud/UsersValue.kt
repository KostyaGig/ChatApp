package ru.zinoview.viewmodelmemoryleak.data.users.cloud

import ru.zinoview.viewmodelmemoryleak.data.core.cloud.AbstractValue

class UsersValue(
    nameValuePairs: CloudUser.Base
) : AbstractValue<CloudUser.Base>(
    nameValuePairs,CloudUser.Base.Empty
)