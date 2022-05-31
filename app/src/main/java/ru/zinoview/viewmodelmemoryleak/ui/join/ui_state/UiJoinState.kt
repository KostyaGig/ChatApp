package ru.zinoview.viewmodelmemoryleak.ui.join.ui_state

import ru.zinoview.viewmodelmemoryleak.core.Save
import ru.zinoview.viewmodelmemoryleak.ui.join.ImageProfile


interface UiJoinState : UiJoinStateAdd, Save<JoinUiStateViewModel> {

    class Base : UiJoinState {

        private var userImage: ImageProfile = ImageProfile.Empty
        private var userNickName = ""

        override fun addImage(image: ImageProfile) { this.userImage = image }
        override fun add(nickName: String) { this.userNickName = nickName }

        override fun save(viewModel: JoinUiStateViewModel) = viewModel.save(
            JoinUiStates.Base(
                JoinUiState.UserNickName(userNickName),
                JoinUiState.UserImageProfile(userImage)
            )
        )
    }
}