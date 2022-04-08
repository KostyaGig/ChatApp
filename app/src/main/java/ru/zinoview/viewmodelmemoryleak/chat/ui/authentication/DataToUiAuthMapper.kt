package ru.zinoview.viewmodelmemoryleak.chat.ui.authentication

import ru.zinoview.viewmodelmemoryleak.chat.core.authentication.Mapper

class DataToUiAuthMapper : Mapper<UiAuthentication> {

    override fun map() = UiAuthentication.Success

    override fun mapFailure() = UiAuthentication.Failure

}