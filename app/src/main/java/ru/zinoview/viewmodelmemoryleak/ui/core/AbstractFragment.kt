package ru.zinoview.viewmodelmemoryleak.ui.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import org.koin.android.ext.android.getKoin
import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.Back
import kotlin.reflect.KClass

abstract class AbstractFragment<VM : ViewModel, B: ViewBinding>(
    private val viewModelClass: KClass<VM>,
) : Fragment(), Back {


    protected val viewModel: VM by lazy {
        getKoin().get<VM>(clazz = viewModelClass)
    }

    abstract fun initBinding(inflater: LayoutInflater,container: ViewGroup?) : B

    private var _binding: B? = null
    protected val binding: B by lazy {
        checkNotNull(_binding)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = initBinding(inflater,container)

        return binding.root
    }
}