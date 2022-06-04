package ru.zinoview.viewmodelmemoryleak.data.chat.ui_state

import android.content.Context
import com.google.gson.Gson
import ru.zinoview.viewmodelmemoryleak.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.data.core.ui_state.UiStateSharedPreferences
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.ChatUiStates

interface ChatUiStateSharedPreferences : UiStateSharedPreferences<ChatUiStates.Base,ChatUiStates> {

    class Base(
        context: Context,
        gson: Gson,
        reader: SharedPreferencesReader<String>
    ) : ChatUiStateSharedPreferences, UiStateSharedPreferences.Base<ChatUiStates.Base,ChatUiStates>(
        ChatUiStates.Base::class.java,
        context,
        gson,
        reader
    ) {
        override fun key() = KEY
        override fun name() = NAME

        private companion object {
            private const val NAME = "chat_state_prefs"
            private const val KEY = "chat_state"
        }

        override fun read(key: Unit, map: (ChatUiStates.Base) -> ChatUiStates)
             = super.read(key) { base -> base.map() }

    }
}
