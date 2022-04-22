package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

import android.app.Activity

interface ActivityNavigation {

    fun navigateTo(activity: Activity,typeFragment: TypeFragment)

    class Base(
        private val mapper: TypeFragmentToExtraMapper,
        private val intent: Intent<String>,
        private val currentActivity: Activity
    ) : ActivityNavigation {

        override fun  navigateTo(activity: Activity,typeFragment: TypeFragment) {
            val stringFragment = mapper.map(typeFragment)
            intent.start(currentActivity,activity,stringFragment)
        }
    }
}