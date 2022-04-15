package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

import ru.zinoview.viewmodelmemoryleak.core.Mapper
import java.lang.IllegalArgumentException


interface StringFragment : Mapper<String, TypeFragment> {

    fun map(src: TypeFragment) : String

    class Base : StringFragment {

        override fun map(src: TypeFragment): String {
            return when(src) {
                is TypeFragment.Join -> JOIN
                is TypeFragment.Chat -> CHAT
                else -> throw IllegalArgumentException("TypeFragmentToExtraMapper doesn't process $src")
            }
        }

        override fun map(src: String): TypeFragment {
            return when(src) {
                JOIN -> TypeFragment.Join
                CHAT -> TypeFragment.Chat
                else -> throw IllegalArgumentException("TypeFragmentToExtraMapper doesn't process $src")
            }
        }

        private companion object {
            private const val JOIN = "join"
            private const val CHAT = "chat"
        }
    }
}