package ru.zinoview.viewmodelmemoryleak.data.users.cloud

interface UpdateUsers : ru.zinoview.viewmodelmemoryleak.data.core.cloud.Update<List<CloudUser>,(List<CloudUser>) -> Unit> {

    class Base(
        private val updateUser: UpdateUser
    ) : UpdateUsers {

        private var isFirstUpdate = true

        override fun update(users: List<CloudUser>, block: (List<CloudUser>) -> Unit) {
            if (isFirstUpdate) {
                block.invoke(users)
                isFirstUpdate = false
            } else updateUser.update(users,block)
        }

    }
}