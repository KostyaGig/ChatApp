package ru.zinoview.viewmodelmemoryleak.chat.data.cloud

import org.json.JSONObject

interface Json {

    fun create(vararg pairs: Pair<String,Any>) : JSONObject

    class Base : Json {

        override fun create(vararg pairs: Pair<String, Any>): JSONObject {
            val json  = JSONObject()
            pairs.forEach { pair ->
                json.put(pair.first,pair.second)
            }
            return json
        }
    }
}