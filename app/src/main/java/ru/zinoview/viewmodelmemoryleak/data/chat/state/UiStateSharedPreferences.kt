package ru.zinoview.viewmodelmemoryleak.data.chat.state

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import ru.zinoview.viewmodelmemoryleak.core.Read
import ru.zinoview.viewmodelmemoryleak.core.Save
import ru.zinoview.viewmodelmemoryleak.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.ui.chat.state.UiStates

interface UiStateSharedPreferences : Save<UiStates>, Read<UiStates,Unit> {


    class Base(
        context: Context,
        private val reader: SharedPreferencesReader<String>,
        private val gson: Gson
    ) : UiStateSharedPreferences {

        private val prefs = context.getSharedPreferences(NAME,Context.MODE_PRIVATE)

        override fun save(data: UiStates) {
            val dataString = gson.toJson(data)
            Log.d("zinoviewk","prefs save state $dataString")
            prefs.edit().putString(KEY,dataString).apply()
        }


        override fun read(key: Unit): UiStates {
            val stringData = reader.read(KEY,prefs)
            Log.d("zinoviewk","prefs read state $stringData")
            return gson.fromJson(stringData, UiStates.Base::class.java)
        }


        private companion object {
            private const val NAME = "state_prefs"
            private const val KEY = "state"
        }

    }

}