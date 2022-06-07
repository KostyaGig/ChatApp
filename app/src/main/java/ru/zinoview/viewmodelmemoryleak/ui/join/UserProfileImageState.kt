package ru.zinoview.viewmodelmemoryleak.ui.join

import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.UiState

interface UserProfileImageState : Mapper<Pair<List<UiState>,List<ViewWrapper>>,Unit>, Dropped<UserProfileImageState> {

    override fun map(src: Pair<List<UiState>, List<ViewWrapper>>) = Unit
    override fun dropped(): UserProfileImageState = UnChosen

    object Chosen : UserProfileImageState {

        override fun map(src: Pair<List<UiState>, List<ViewWrapper>>) {
            val text = src.second[1]
            text.show(Unit)

            src.first.forEach { state -> state.recover(text) }
        }
    }

    object UnChosen : UserProfileImageState {
        override fun map(src: Pair<List<UiState>, List<ViewWrapper>>) {

            val image = src.second.first()
            val text = src.second[1]

            src.first.forEach { state -> state.recover(image, text) }
        }
    }
}