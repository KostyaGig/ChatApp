package ru.zinoview.viewmodelmemoryleak.ui.join.ui_state

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.Save
import ru.zinoview.viewmodelmemoryleak.ui.join.ImageProfile

interface UiJoinState : UiJoinStateAdd, Save<JoinUiStateViewModel> {

    class Base : UiJoinState {

        private var userImage: ImageProfile = ImageProfile.Empty
        private var userNickName = ""

        override fun addImage(image: ImageProfile) { this.userImage = image }
        override fun add(nickName: String) { this.userNickName = nickName }

        override fun save(viewModel: JoinUiStateViewModel) {

            Log.d("zinoviewk","save state , image - $userImage")
            viewModel.save(
                JoinUiStates.Base(
                    JoinUiState.UserNickName(userNickName),
                    JoinUiState.UserImageProfile(userImage)
                )
            )
        }

        override fun toString(): String {
            return "$userImage"
        }
    }
}