package ru.zinoview.viewmodelmemoryleak.chat.ui.join

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import io.socket.client.IO
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.abstract_ex.AbstractFragment
import ru.zinoview.viewmodelmemoryleak.chat.core.ExceptionMapper
import ru.zinoview.viewmodelmemoryleak.chat.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.chat.core.navigation.Navigation
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.Id
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.ActivityConnection
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.Json
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.SocketConnection
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.join.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.join.JoinUserRepository
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view.MessageField
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view.SnackBar
import ru.zinoview.viewmodelmemoryleak.databinding.JoinFragmentBinding

class JoinUserFragment : AbstractFragment<JoinUserViewModel.Base,JoinFragmentBinding>(
    JoinUserViewModel.Base::class
) {

    override fun factory(): ViewModelProvider.Factory {
        val id = Id.Base()
        return JoinUserViewModelFactory.Base(
            JoinUserRepository.Base(
                IdSharedPreferences.Base(
                    SharedPreferencesReader.Base(id),
                    id,
                    requireContext()
                ),
                CloudDataSource.Base(
                    IO.socket("http://10.0.2.2:3000"),
                    SocketConnection.Base(
                        ActivityConnection.Base()
                    ),
                    Json.Base()
                ),
                ExceptionMapper.Base(
                    ResourceProvider.Base(requireContext())
                )
            ),
        )
    }

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
                SnackBar.Base(binding.joinBtn)
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