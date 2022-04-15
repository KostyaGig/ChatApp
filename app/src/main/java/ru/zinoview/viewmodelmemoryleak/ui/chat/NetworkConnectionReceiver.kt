package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager


interface NetworkConnectionReceiver {

    fun register(context: Context)

    fun unRegister(context: Context)

    class Base(
        private val viewModel: ru.zinoview.viewmodelmemoryleak.ui.core.Connection
    ) : NetworkConnectionReceiver, BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
                val connectivityManager= context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo=connectivityManager.activeNetworkInfo
                val result = networkInfo!=null && networkInfo.isConnected

                viewModel.checkNetworkConnection(result)
            }
        }

        override fun register(context: Context) {
            val filter = IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION
            )
            context.registerReceiver(this,filter)
        }

        override fun unRegister(context: Context) = context.unregisterReceiver(this)
    }

    object Empty : NetworkConnectionReceiver {
        override fun register(context: Context) = Unit

        override fun unRegister(context: Context) = Unit
    }
}