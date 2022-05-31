package ru.zinoview.viewmodelmemoryleak.ui.join.ui_state

import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.UiState
import ru.zinoview.viewmodelmemoryleak.ui.join.Base64Image
import ru.zinoview.viewmodelmemoryleak.ui.join.ImageProfile


interface JoinUiState : Mapper<Base64Image,String>, UiState {


    data class UserNickName(
        private val userNickName: String
    ) :  JoinUiState {

        override fun map(src: Base64Image) = userNickName
        override fun recover(image: ViewWrapper, text: ViewWrapper)
            = text.show(Unit,userNickName)
    }

    data class UserImageProfile(
        private val imageProfile: ImageProfile
    ):  JoinUiState {

        override fun map(src: Base64Image)
            = imageProfile.base64Image(src)

        override fun recover(image: ViewWrapper, text: ViewWrapper)
            = imageProfile.show(image)
    }

}