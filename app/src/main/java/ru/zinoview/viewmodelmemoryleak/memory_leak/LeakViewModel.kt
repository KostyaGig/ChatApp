package ru.zinoview.viewmodelmemoryleak.memory_leak

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

interface LeakViewModel {

    fun addView(activity: Activity) = Unit

    fun print() = Unit

    fun clear() = Unit


    class Base : LeakViewModel, ViewModel() {

        private val livedata = MutableLiveData<Any>()

        private val diedViews = mutableListOf<Activity>()

        init {
            Log.d("zinoviewk","init viewmodel")
        }

        override fun addView(activity: Activity) {
            diedViews.add(activity)
        }

        override fun print() {
            Log.d("zinoviewk",diedViews.toString())
        }

        override fun clear() {
            diedViews.clear()
        }
    }

    class Empty : LeakViewModel
}