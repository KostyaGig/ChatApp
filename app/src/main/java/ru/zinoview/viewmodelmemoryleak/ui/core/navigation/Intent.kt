package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

import android.app.Activity
import android.content.Intent
import android.util.Log

interface Intent<T>  {

    fun start(currentActivity: Activity,activity: Activity,data: T)

    fun navigate(intent: Intent,navigation: Navigation)

    fun navigate(navigation: Navigation)

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
            val data = intent.getStringExtra(FRAGMENT_KEY) ?: EMPTY
            val fragment = mapper.map(data)
            fragment.navigate(navigation)

            Log.d("zinoviewk","navigate save $data")
            fragmentStore.save(data)
        }

        override fun navigate(navigation: Navigation)
            = fragmentStore.navigate(navigation)

        override fun saveFragment(fragment: androidx.fragment.app.Fragment)
            = fragmentStore.save(fragment)

        private companion object {
            private const val FRAGMENT_KEY = "fragment_key"
            private const val EMPTY = ""
        }
    }

    interface FragmentStore {

        fun save(stringFragment: String)

        fun save(fragment: androidx.fragment.app.Fragment)

        // todo move
        fun navigate(navigation: Navigation)

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