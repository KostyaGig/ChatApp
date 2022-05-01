package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

import androidx.fragment.app.Fragment

interface Navigation : Back, Exit {

    fun navigateTo(fragment: Fragment,notificationMessageId: String = "")
}