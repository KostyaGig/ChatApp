package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log


interface NetworkConnectionReceiver {

    fun register(context: Context)

    fun unRegister(context: Context)

    class Base(
        private val viewModel: ru.zinoview.viewmodelmemoryleak.ui.core.ConnectionViewModel
    ) : NetworkConnectionReceiver, BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
                val connectivityManager= context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo=connectivityManager.activeNetworkInfo
                val isConnected = networkInfo!=null && networkInfo.isConnected

                Log.d("zinoviewk","receiver send connection $isConnected")
                viewModel.updateNetworkState(isConnected,Unit)
            }
        }

        override fun register(context: Context) {
            val filter = IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION
            )
            context.registerReceiver(this,filter)
            Log.d("zinoviewk","reg receiver")
        }

        override fun unRegister(context: Context) {
            Log.d("zinoviewk","unreg receiver")
            context.unregisterReceiver(this)
        }
    }

    object Empty : NetworkConnectionReceiver {
        override fun register(context: Context) {
            Log.d("zinoviewk","reg receiver empty")
        }

        override fun unRegister(context: Context) {
            Log.d("zinoviewk","unreg receiver empty")
        }
    }
}