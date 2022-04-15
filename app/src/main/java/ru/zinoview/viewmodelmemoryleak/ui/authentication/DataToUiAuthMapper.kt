package ru.zinoview.viewmodelmemoryleak.ui.authentication

import ru.zinoview.viewmodelmemoryleak.core.authentication.Mapper

class DataToUiAuthMapper :
    Mapper<UiAuthentication> {

    override fun map() = UiAuthentication.Success

    override fun mapFailure() = UiAuthentication.Failure

}