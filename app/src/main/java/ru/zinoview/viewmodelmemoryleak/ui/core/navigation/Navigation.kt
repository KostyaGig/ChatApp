package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

import androidx.fragment.app.Fragment

interface Navigation : Exit {

    fun navigateTo(fragment: Fragment,data: NavigationData = NavigationData.Empty)
}