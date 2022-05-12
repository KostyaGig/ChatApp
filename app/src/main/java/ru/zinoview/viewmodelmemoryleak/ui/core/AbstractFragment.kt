package ru.zinoview.viewmodelmemoryleak.ui.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import org.koin.android.ext.android.getKoin
import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.ui.core.koin_scope.KoinScope
import ru.zinoview.viewmodelmemoryleak.ui.core.koin_scope.ScreenScope
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.Back
import kotlin.reflect.KClass

abstract class AbstractFragment<VM : ViewModel, B: ViewBinding>(
    private val viewModelClass: KClass<VM>,
) : Fragment(), Back {

    private val scope = KoinScope.Base()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scope = KoinScope.Base()
        koinScopes().forEach { screenScope ->
            scope.scope(screenScope,getKoin())
        }
    }

    protected val viewModel: VM by lazy {
        getKoin().get(clazz = viewModelClass)
    }

    abstract fun initBinding(inflater: LayoutInflater,container: ViewGroup?) : B

    abstract fun koinScopes() : List<ScreenScope>

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        scope.clean(getKoin())
        (viewModel as Clean).clean()
    }
}