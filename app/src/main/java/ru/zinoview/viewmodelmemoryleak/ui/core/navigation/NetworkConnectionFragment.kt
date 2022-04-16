package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import ru.zinoview.viewmodelmemoryleak.ui.chat.NetworkConnectionReceiver
import ru.zinoview.viewmodelmemoryleak.ui.core.AbstractFragment
import kotlin.reflect.KClass

abstract class NetworkConnectionFragment<VM : ViewModel, B: ViewBinding>(
    viewModelClass: KClass<VM>,
) : AbstractFragment<VM,B>(viewModelClass) {

    protected var networkConnectionReceiver: NetworkConnectionReceiver = NetworkConnectionReceiver.Empty

    override fun onStart() {
        super.onStart()
        networkConnectionReceiver.register(requireActivity().applicationContext)
    }

    override fun onPause() {
        super.onPause()
        networkConnectionReceiver.unRegister(requireActivity().applicationContext)
    }
}