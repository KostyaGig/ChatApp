package ru.zinoview.viewmodelmemoryleak.chat.data.connection.cloud

import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.Subscribe

interface ConnectionState : Subscribe<CloudConnection> {


    fun change(state: Boolean,message: String = "")

    class Base : ConnectionState {

        private var block: (CloudConnection) -> Unit = {}

        override fun change(state: Boolean, message: String) {
            if (!state) {
                block.invoke(CloudConnection.ToolBarConnection(message))
            }
        }

        override fun subscribe(block: (CloudConnection) -> Unit) {
            this.block = block
        }

    }
}