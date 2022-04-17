package ru.zinoview.viewmodelmemoryleak.ui.join

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.databinding.JoinFragmentBinding
import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatFragment
import ru.zinoview.viewmodelmemoryleak.ui.chat.NetworkConnectionReceiver
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.MessageField
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBar
import ru.zinoview.viewmodelmemoryleak.ui.core.ToolbarActivity
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.Navigation
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.NetworkConnectionFragment

class JoinUserFragment : NetworkConnectionFragment<JoinUserViewModel.Base, JoinFragmentBinding>(
    JoinUserViewModel.Base::class
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkConnectionReceiver = NetworkConnectionReceiver.Base(viewModel)

        binding.joinBtn.setOnClickListener {
//            val nicknameField = view.findViewById<MessageField.Base>(R.id.nickname_field)
//            nicknameField.doAction(viewModel)
            (requireActivity() as Navigation).navigateTo(ChatFragment())
        }

        viewModel.connection()
    }

    override fun onStart() {
        super.onStart()

        viewModel.observe(this) { uiJoin ->
            uiJoin.navigate(requireActivity() as Navigation)
            uiJoin.showError(
                SnackBar.Base(binding.joinBtn, SnackBar.SnackBarVisibility.Empty())
            )
        }

        viewModel.observeConnection(this) { connection ->
            connection.changeTitle(requireActivity() as ToolbarActivity)
        }
    }

    override fun back(navigation: Navigation) = navigation.exit()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): JoinFragmentBinding
        = JoinFragmentBinding.inflate(inflater,container,false)

    override fun dependenciesScope() = SCOPE_NAME

    private companion object {
        private const val SCOPE_NAME = "jufScope"
    }
}