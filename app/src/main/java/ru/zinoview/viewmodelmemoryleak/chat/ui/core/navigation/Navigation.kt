package ru.zinoview.viewmodelmemoryleak.chat.ui.core.navigation

import androidx.fragment.app.Fragment

interface Navigation : Back, Exit {

    fun navigateTo(fragment: Fragment)
}