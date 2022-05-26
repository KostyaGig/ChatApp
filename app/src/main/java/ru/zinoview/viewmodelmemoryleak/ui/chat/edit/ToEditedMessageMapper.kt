package ru.zinoview.viewmodelmemoryleak.ui.chat.edit

import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.ui.users.BundleUser
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiMessage

class ToEditedMessageMapper(
    private val bundleUser: BundleUser
) : Mapper.Base<UiMessage.EditedMessage>(UiMessage.EditedMessage.Empty) {

    override fun map(
        id: String,
        senderId: String,
        content: String,
        senderNickname: String
    ) = bundleUser.map(id)

}