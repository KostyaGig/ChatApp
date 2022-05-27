package ru.zinoview.viewmodelmemoryleak.data.chat.ui_state

import android.content.Context
import com.google.gson.Gson
import ru.zinoview.viewmodelmemoryleak.core.Read
import ru.zinoview.viewmodelmemoryleak.core.Save
import ru.zinoview.viewmodelmemoryleak.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStates
import ru.zinoview.viewmodelmemoryleak.ui.join.Base64Image
import ru.zinoview.viewmodelmemoryleak.ui.join.Bitmap
import ru.zinoview.viewmodelmemoryleak.ui.join.ImageProfile
import ru.zinoview.viewmodelmemoryleak.ui.join.ui_state.JoinUiState
import ru.zinoview.viewmodelmemoryleak.ui.join.ui_state.JoinUiStates

interface UiStateSharedPreferences<T> : Save<T>, Read<T, Unit> {

    abstract class Base<T>(
        private val clazz: Class<T>,
        context: Context,
        private val gson: Gson,
        private val reader: SharedPreferencesReader<String>
    ) : UiStateSharedPreferences<T> {

        abstract fun key(): String
        abstract fun name(): String

        private val prefs by lazy {
            context.getSharedPreferences(key(), Context.MODE_PRIVATE)
        }

        override fun save(data: T) {
            val dataString = gson.toJson(data)
            prefs.edit().putString(key(), dataString).apply()
        }

        override fun read(key: Unit): T {
            val stringData = reader.read(key(), prefs)
            return gson.fromJson(stringData, clazz)
        }
    }

    class Chat(
        context: Context,
        gson: Gson,
        reader: SharedPreferencesReader<String>
    ) : Base<UiStates.Base>(UiStates.Base::class.java, context, gson, reader) {

        override fun key() = KEY
        override fun name() = NAME

        private companion object {
            private const val NAME = "chat_state_prefs"
            private const val KEY = "chat_state"
        }
    }


    class Join(
        context: Context,
        private val reader: SharedPreferencesReader<String>,
        private val base64Image: Base64Image,
        private val bitmap: Bitmap
    ) : UiStateSharedPreferences<JoinUiStates.Base> {

        private val prefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

        override fun save(data: JoinUiStates.Base) {
            val pair = data.map(base64Image)

            prefs.edit().putString(NICK_NAME_KEY, pair.first).apply()
            prefs.edit().putString(IMAGE_KEY, pair.second).apply()
        }

        override fun read(key: Unit): JoinUiStates.Base {
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

