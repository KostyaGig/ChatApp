package ru.zinoview.viewmodelmemoryleak.abstract_ex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

interface TapViewModelFactory : ViewModelProvider.Factory {

    class Base(
        private val tap: Tap
    ) : TapViewModelFactory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T
            = TapViewModel.Base(tap) as T
    }
}