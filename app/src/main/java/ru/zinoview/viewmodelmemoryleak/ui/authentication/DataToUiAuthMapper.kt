package ru.zinoview.viewmodelmemoryleak.ui.authentication

import ru.zinoview.viewmodelmemoryleak.core.authentication.Mapper

class DataToUiAuthMapper :
    Mapper<UiAuth> {

    override fun map() = UiAuth.Success

    override fun mapFailure() = UiAuth.Failure

}