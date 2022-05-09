package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import ru.zinoview.viewmodelmemoryleak.data.core.cloud.CloudDataWrapper

class TypingMessagesWrapperDataWrapper(
    values: ArrayList<TypingMessageValue>
) : CloudDataWrapper<CloudMessage.Typing, TypingMessageValue>(values,CloudMessage.Typing.Empty)