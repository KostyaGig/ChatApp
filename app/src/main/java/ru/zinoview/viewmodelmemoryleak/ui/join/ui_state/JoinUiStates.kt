package ru.zinoview.viewmodelmemoryleak.ui.join.ui_state

import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStateCommunication
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStates
import ru.zinoview.viewmodelmemoryleak.ui.join.Base64Image

interface JoinUiStates : UiStates, Mapper<Base64Image,Pair<String,String>> {

    // todo
    fun map(communication: JoinUiStateCommunication)

    class Base(
        private val userNickName: JoinUiState.UserNickName,
        private val userProfileImage: JoinUiState.UserImageProfile
    ) : JoinUiStates {

        override fun map(src: Base64Image): Pair<String, String> {
            val stringImage = userProfileImage.map(src)
            val stringUserNickName = userNickName.map(src)
            return Pair(stringUserNickName,stringImage)
        }

        override fun map(communication: JoinUiStateCommunication)
            = communication.postValue(listOf(userNickName,userProfileImage))

        override fun map(communication: UiStateCommunication)
            = communication.postValue(listOf(userNickName,userProfileImage))
    }
}