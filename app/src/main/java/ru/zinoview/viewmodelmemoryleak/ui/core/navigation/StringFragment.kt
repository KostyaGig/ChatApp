package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

import androidx.fragment.app.Fragment
import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatFragment
import ru.zinoview.viewmodelmemoryleak.ui.join.JoinUserFragment
import ru.zinoview.viewmodelmemoryleak.ui.users.UsersFragment
import java.lang.IllegalArgumentException


interface StringFragment : Mapper<String, TypeFragment> {

    fun map(src: TypeFragment) : String

    fun map(src: Fragment) : String

    class Base : StringFragment {

        override fun map(src: TypeFragment): String {
            return when(src) {
                is TypeFragment.Join -> JOIN
                is TypeFragment.Users -> USERS
                is TypeFragment.Chat -> CHAT
                else -> throw IllegalArgumentException("TypeFragmentToExtraMapper doesn't process $src")
            }
        }

        override fun map(src: String): TypeFragment {
            return when(src) {
                JOIN -> TypeFragment.Join
                USERS -> TypeFragment.Users
                CHAT -> TypeFragment.Chat
                else -> throw IllegalArgumentException("TypeFragmentToExtraMapper doesn't process $src")
            }
        }

        override fun map(src: Fragment): String {
            return when(src.javaClass) {
                JoinUserFragment::class.java -> JOIN
                UsersFragment::class.java -> USERS
                ChatFragment::class.java -> CHAT
                else -> throw IllegalArgumentException("TypeFragmentToExtraMapper doesn't process $src")
            }
        }


        private companion object {
            private const val JOIN = "join"
            private const val USERS = "users"
            private const val CHAT = "chat"
        }
    }
}