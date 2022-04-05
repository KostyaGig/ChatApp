package ru.zinoview.viewmodelmemoryleak.abstract_ex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.databinding.TapFragmentBinding

class TapFragment : AbstractFragment<TapViewModel.Base, TapFragmentBinding>(
    TapViewModel.Base::class
) {
    override fun factory(): ViewModelProvider.Factory {
        // inject viewModelFactory using di
        return TapViewModelFactory.Base(
            Tap.Base(
                Print.Base()
            )
        )
    }


    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): TapFragmentBinding
        = TapFragmentBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tapBtn.setOnClickListener {
            viewModel.tap()
            viewModel.printTapCount()
        }
    }


}