package ru.zinoview.viewmodelmemoryleak.ui.users

import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.TextView
import ru.zinoview.viewmodelmemoryleak.ui.core.*

sealed class UiUser : DiffSame<UiUser>, SameOne<String>, Same<String,Unit>, UserBind, UiItem, OnClick<UserItemClickListener> {

    override fun isContentTheSame(item: UiUser) = false
    override fun isItemTheSame(item: UiUser) = false
    override fun same(data: String) = false
    override fun same(arg1: String, arg2: Unit) = false

    override fun bind(view: Pair<TextView, ImageView>) = Unit
    override fun bindLastMessage(view: TextView) = Unit

    override fun onClick(item: UserItemClickListener) = Unit

    class Base(
        private val id: String,
        private val nickName: String,
        private val lastMessageText: String,
        private val image: Bitmap,
    ): UiUser() {

        override fun isContentTheSame(item: UiUser)
            = item.same(id)

        override fun isItemTheSame(item: UiUser)
            = item.same(nickName,Unit)

        override fun same(id: String)
            = this.id == id

        override fun same(nickName: String, arg2: Unit)
            = this.nickName == nickName

        override fun bind(view: Pair<TextView, ImageView>) {
            view.first.text = nickName
            view.second.setImageBitmap(image)
        }

        override fun bindLastMessage(view: TextView) {
            view.text = lastMessageText
        }

        override fun onClick(listener: UserItemClickListener) = listener.onClick(id)

    }
}