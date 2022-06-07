package ru.zinoview.viewmodelmemoryleak.data.users.cloud


interface UpdateUser : ru.zinoview.viewmodelmemoryleak.data.core.cloud.Update<List<CloudUser>,(List<CloudUser>) -> Unit> {

    class Base : UpdateUser {

        override fun update(users: List<CloudUser>, block: (List<CloudUser>) -> Unit) {
            var update = true
            if (users.isNotEmpty()) {
                users.forEach { cloudUser ->
                    if (cloudUser.isNotUpdated()) {
                        update = false
                    }
                }
            } else {
                update = false
            }
            if (update) {
                block.invoke(users)
            }
        }
    }
}