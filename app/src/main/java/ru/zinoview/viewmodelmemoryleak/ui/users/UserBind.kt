package ru.zinoview.viewmodelmemoryleak.ui.users

import android.widget.ImageView
import android.widget.TextView
import ru.zinoview.viewmodelmemoryleak.ui.core.Bind

interface UserBind : Bind<Pair<TextView, ImageView>> {

    fun bindLastMessage(view: TextView)
}