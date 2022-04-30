package ru.zinoview.viewmodelmemoryleak.data.cache

interface UserSharedPreferences {

    fun id() : String

    fun nickName() : String

    class Base(
        private val id: IdSharedPreferences<String,Unit>,
        private val name: NickNameSharedPreferences<String,Unit>,
    ) : UserSharedPreferences {

        override fun id() = id.read(Unit)
        override fun nickName() = name.read(Unit)
    }
}