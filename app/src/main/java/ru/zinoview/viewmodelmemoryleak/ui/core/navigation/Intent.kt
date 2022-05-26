package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

import android.app.Activity
import android.content.Intent
import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.Save

interface Intent<T> : Navigate {

    fun start(currentActivity: Activity,activity: Activity,data: T)

    fun navigate(intent: Intent,navigation: Navigation)

    fun saveFragment(fragment: androidx.fragment.app.Fragment)

    class Fragment(
        private val mapper: ExtraToTypeFragmentMapper,
        private val fragmentStore: FragmentStore
    ) : ru.zinoview.viewmodelmemoryleak.ui.core.navigation.Intent<String> {

        override fun start(currentActivity: Activity,activity: Activity, data: String) {
            val intent = Intent(currentActivity,activity::class.java)
            intent.putExtra(FRAGMENT_KEY,data)
            currentActivity.startActivity(intent)
            currentActivity.finish()
        }


        override fun navigate(intent: Intent,navigation: Navigation) {
            val stringFragment = intent.getStringExtra(FRAGMENT_KEY) ?: EMPTY
            Log.d("zinoviewk","navigate $stringFragment")

            // todo replace on parcelable
            val intentNotificationMessageId = intent.getStringExtra(NOTIFICATION_MESSAGE_KEY)

            val notificationMessage = intentNotificationMessageId ?: ""

            val fragment = mapper.map(stringFragment)
            fragment.navigate(navigation,notificationMessage)

            fragmentStore.save(stringFragment)
        }

        override fun navigate(navigation: Navigation)
            = fragmentStore.navigate(navigation)

        override fun saveFragment(fragment: androidx.fragment.app.Fragment)
            = fragmentStore.save(fragment)

        private companion object {
            private const val FRAGMENT_KEY = "fragment_key"
            private const val NOTIFICATION_MESSAGE_KEY = "message_id"
            private const val EMPTY = ""
        }
    }

    interface FragmentStore : Navigate, Save<String> {

        fun save(fragment: androidx.fragment.app.Fragment)

        class Base(
            private val stringFragment: StringFragment,
            private val mapper: ExtraToTypeFragmentMapper,
            private val prefs: FragmentSharedPreferences
        ) : FragmentStore {

            override fun save(stringFragment: String)
                = prefs.save(stringFragment)

            override fun save(fragment: androidx.fragment.app.Fragment) {
                val stringFragment = stringFragment.map(fragment)
                save(stringFragment)
            }

            override fun navigate(navigation: Navigation) {
                val stringFragment = prefs.read()

                val fragment = mapper.map(stringFragment)
                fragment.navigate(navigation)
            }

        }
    }
}