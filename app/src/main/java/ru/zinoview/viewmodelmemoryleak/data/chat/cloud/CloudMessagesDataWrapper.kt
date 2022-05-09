package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import ru.zinoview.viewmodelmemoryleak.data.core.cloud.CloudDataWrapper

class CloudMessagesDataWrapper(
    values: ArrayList<CloudMessagesValue>
) : CloudDataWrapper<CloudMessage.Base, CloudMessagesValue>(values,CloudMessage.Base.Empty)