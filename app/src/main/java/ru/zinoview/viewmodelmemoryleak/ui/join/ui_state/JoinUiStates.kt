package ru.zinoview.viewmodelmemoryleak.ui.join.ui_state

import ru.zinoview.viewmodelmemoryleak.data.core.ui_state.AbstractModel
import ru.zinoview.viewmodelmemoryleak.ui.core.Communication
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.UiState
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.UiStates
import ru.zinoview.viewmodelmemoryleak.ui.join.Base64Image

interface JoinUiStates : UiStates, AbstractModel<JoinUiStates> {

    fun map(base64Image: Base64Image) : Pair<String,String> = Pair("","")
    override fun map(): JoinUiStates = Empty

    class Base(
        private val userNickName: JoinUiState.UserNickName,
        private val userProfileImage: JoinUiState.UserImageProfile
    ) : JoinUiStates {

        override fun map(base64Image: Base64Image): Pair<String, String> {
            val stringImage = userProfileImage.map(base64Image)
            val stringUserNickName = userNickName.map(base64Image)
            return Pair(stringUserNickName,stringImage)
        }

        override fun map(communication: Communication<List<UiState>>)
            = communication.postValue(listOf(userNickName,userProfileImage))
    }

    object Empty : JoinUiStates
}