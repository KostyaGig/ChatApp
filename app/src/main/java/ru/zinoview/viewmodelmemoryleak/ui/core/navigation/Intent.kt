package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent

interface Intent<T>  {

    fun start(currentActivity: Activity,activity: Activity,data: T)

    fun navigate(intent: Intent,navigation: Navigation)

    class Fragment(
        private val mapper: ExtraToTypeFragmentMapper
    ) : ru.zinoview.viewmodelmemoryleak.ui.core.navigation.Intent<String> {

        override fun start(currentActivity: Activity,activity: Activity, data: String) {
            val intent = Intent(currentActivity,activity::class.java)
            intent.putExtra(FRAGMENT_KEY,data)
            currentActivity.startActivity(intent)
            currentActivity.finish()
        }


        override fun navigate(intent: Intent,navigation: Navigation) {
            val data = intent.getStringExtra(FRAGMENT_KEY)
            val fragment = mapper.map(data ?: EMPTY)
            fragment.navigate(navigation)
        }

        private companion object {
            private const val FRAGMENT_KEY = "fragment_key"
            private const val EMPTY = ""
        }
    }
}