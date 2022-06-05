package ru.zinoview.viewmodelmemoryleak.ui.join.ui_state

import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.UiState
import ru.zinoview.viewmodelmemoryleak.ui.join.ImageProfile

interface ImageUiState {

    fun imageProfile(state: List<UiState>) : ImageProfile

    class Base : ImageUiState {

        override fun imageProfile(state: List<UiState>): ImageProfile {
            return if (state.size < STATE_WITH_IMAGE_SIZE) {
                ImageProfile.Empty
            } else {
                val imageState = state[IMAGE_PROFILE_INDEX]
                val result = imageState.map(imageState as Mapper<Unit, ImageProfile>)

                result
            }
        }

        private companion object {
            private const val STATE_WITH_IMAGE_SIZE = 2
            private const val IMAGE_PROFILE_INDEX = 1
        }
    }
}