package ru.zinoview.viewmodelmemoryleak.ui.core.ui_state

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import org.koin.android.ext.android.getKoin
import ru.zinoview.viewmodelmemoryleak.ui.core.AbstractFragment
import kotlin.reflect.KClass

abstract class SaveUiStateFragment<BVM : ViewModel, SVM : UiStateViewModel<*,*> ,B : ViewBinding>(
    baseViewModelClazz: KClass<BVM>,
    uiStateViewModelClazz: KClass<SVM>
) : AbstractFragment<BVM,B>(baseViewModelClazz)  {

    protected val uiStateViewModel: SVM by lazy { getKoin().get(clazz = uiStateViewModelClazz) }

    abstract fun recoverStateAfterLaunch() : Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (recoverStateAfterLaunch()) {
            uiStateViewModel.read(Unit)
        } else {
            if (savedInstanceState != null) {
                uiStateViewModel.read(Unit)
            }
        }
    }
}