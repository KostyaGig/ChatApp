package ru.zinoview.viewmodelmemoryleak.data.join.ui_state

import android.content.Context
import ru.zinoview.viewmodelmemoryleak.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.data.core.ui_state.UiStateSharedPreferences
import ru.zinoview.viewmodelmemoryleak.ui.join.Base64Image
import ru.zinoview.viewmodelmemoryleak.ui.join.Bitmap
import ru.zinoview.viewmodelmemoryleak.ui.join.ImageProfile
import ru.zinoview.viewmodelmemoryleak.ui.join.ui_state.JoinUiState
import ru.zinoview.viewmodelmemoryleak.ui.join.ui_state.JoinUiStates

interface JoinUiStateSharedPreferences : UiStateSharedPreferences<JoinUiStates.Base,JoinUiStates> {

    class Base(
        context: Context,
        private val reader: SharedPreferencesReader<String>,
        private val base64Image: Base64Image,
        private val bitmap: Bitmap
    ) : JoinUiStateSharedPreferences {

        private val prefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

        override fun save(data: JoinUiStates.Base) {
            val pair = data.map(base64Image)

            prefs.edit().putString(NICK_NAME_KEY, pair.first).apply()
            prefs.edit().putString(IMAGE_KEY, pair.second).apply()
        }

        override fun read(key: Unit, map: (JoinUiStates.Base) -> JoinUiStates): JoinUiStates {
            val image = reader.read(IMAGE_KEY, prefs)
            val nickName = reader.read(NICK_NAME_KEY,prefs)

            val imageProfile = if (image.isNotEmpty()) {
                ImageProfile.Bitmap(
                    bitmap.bitmap(image)
                )
            } else {
                ImageProfile.Empty
            }

            return JoinUiStates.Base(
                JoinUiState.UserNickName(nickName),
                JoinUiState.UserImageProfile(imageProfile)
            )
        }

        private companion object {
            private const val NAME = "join_state_prefs"
            private const val NICK_NAME_KEY = "nick_name_key"
            private const val IMAGE_KEY = "image_key"
        }
    }
}
