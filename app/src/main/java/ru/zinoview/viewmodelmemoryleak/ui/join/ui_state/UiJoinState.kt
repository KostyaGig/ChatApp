package ru.zinoview.viewmodelmemoryleak.ui.join.ui_state

import ru.zinoview.viewmodelmemoryleak.core.Add
import ru.zinoview.viewmodelmemoryleak.core.Save
import ru.zinoview.viewmodelmemoryleak.ui.join.ImageProfile

interface UiJoinState : Add<ImageProfile>, Save<JoinUiStateViewModel> {

    class Base: UiJoinState {

        private var userImage: ImageProfile = ImageProfile.Empty

        override fun add(image: ImageProfile) { this.userImage = image }

        override fun save(viewModel: JoinUiStateViewModel) {

            viewModel.save(
                JoinUiStates.Base(userProfileImage = JoinUiState.UserImageProfile(userImage))
            )
        }

        override fun toString(): String {
            return "$userImage"
        }
    }
}