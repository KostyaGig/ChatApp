package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import ru.zinoview.viewmodelmemoryleak.data.core.cloud.AbstractValue

class CloudMessagesValue(
    nameValuePairs: CloudMessage.Base
) : AbstractValue<CloudMessage.Base>(
    nameValuePairs,
    CloudMessage.Base.Empty
)
