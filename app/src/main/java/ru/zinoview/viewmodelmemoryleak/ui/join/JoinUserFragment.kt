package ru.zinoview.viewmodelmemoryleak.ui.join

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.databinding.JoinFragmentBinding
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.MessageField
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBar
import ru.zinoview.viewmodelmemoryleak.ui.core.AbstractFragment
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.Navigation

class JoinUserFragment : AbstractFragment<JoinUserViewModel.Base, JoinFragmentBinding>(
    JoinUserViewModel.Base::class
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.joinBtn.setOnClickListener {
            val nicknameField = view.findViewById<MessageField.Base>(R.id.nickname_field)
            nicknameField.doAction(viewModel)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.observe(this) { uiJoin ->
            uiJoin.navigate(requireActivity() as Navigation)
            uiJoin.showError(
                SnackBar.Base(binding.joinBtn, SnackBar.SnackBarVisibility.Empty())
            )
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.clean()
    }

    override fun back(navigation: Navigation) = navigation.exit()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): JoinFragmentBinding
        = JoinFragmentBinding.inflate(inflater,container,false)
}