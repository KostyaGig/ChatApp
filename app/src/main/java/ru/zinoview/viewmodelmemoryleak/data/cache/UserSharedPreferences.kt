package ru.zinoview.viewmodelmemoryleak.data.cache

interface UserSharedPreferences {

    fun id() : Int

    fun nickName() : String

    class Base(
        private val id: IdSharedPreferences<Int,Unit>,
        private val name: NickNameSharedPreferences<String,Unit>,
    ) : UserSharedPreferences {

        override fun id() = id.read(Unit)
        override fun nickName() = name.read(Unit)
    }
}