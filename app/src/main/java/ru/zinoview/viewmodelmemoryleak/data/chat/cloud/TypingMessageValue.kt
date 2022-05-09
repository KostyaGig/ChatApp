package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import ru.zinoview.viewmodelmemoryleak.data.core.cloud.AbstractValue

class TypingMessageValue(
    nameValuePairs: CloudMessage.Typing
) : AbstractValue<CloudMessage.Typing>(
    nameValuePairs,CloudMessage.Typing.Empty
)