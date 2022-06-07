package ru.zinoview.viewmodelmemoryleak.ui.join.ui_state

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty
import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.UiState
import ru.zinoview.viewmodelmemoryleak.ui.join.Base64Image
import ru.zinoview.viewmodelmemoryleak.ui.join.ImageProfile

interface JoinUiState :  UiState, Mapper<Unit,ImageProfile>, IsNotEmpty<Unit> {

    fun state(src: Base64Image) : String
    override fun isNotEmpty(arg: Unit): Boolean = false

    override fun map(src: Unit): ImageProfile = ImageProfile.Empty

    data class UserNickName(
        private val userNickName: String
    ) :  JoinUiState {

        override fun state(src: Base64Image) =  userNickName

        override fun recover(image: ViewWrapper, text: ViewWrapper)
            = text.show(Unit,userNickName)

        override fun recover(text: ViewWrapper) = text.show(Unit,userNickName)

        override fun isNotEmpty(arg: Unit) = userNickName.isNotEmpty()
    }

    data class UserImageProfile(
        private val imageProfile: ImageProfile
    ):  JoinUiState {

        override fun state(src: Base64Image) = imageProfile.base64Image(src)

        override fun recover(image: ViewWrapper, text: ViewWrapper)
            = imageProfile.show(image)

        override fun <T> map(mapper: Mapper<Unit, T>): T
            = mapper.map(Unit)

        override fun map(src: Unit) = imageProfile.map(Unit)
        override fun isNotEmpty(arg: Unit) = (imageProfile is ImageProfile.Empty).not()
    }

}