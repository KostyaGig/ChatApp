package ru.zinoview.viewmodelmemoryleak.data.core.cloud

import com.google.gson.Gson
import org.json.JSONObject
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.WrapperMessages

interface Json {

    fun json(vararg pairs: Pair<String,Any>) : JSONObject

    fun json(obj: Any) : String

    fun <T> objectFromJson(json: String,clazz:Class<T>) : T

    class Base(
        private val gson: Gson
    ) : Json {

        override fun json(vararg pairs: Pair<String, Any>): JSONObject {
            val json  = JSONObject()
            pairs.forEach { pair ->
                json.put(pair.first,pair.second)
            }
            return json
        }

        override fun json(obj: Any): String = gson.toJson(obj)

        override fun <T> objectFromJson(json: String, clazz: Class<T>): T
            = gson.fromJson(json, clazz)
    }
}