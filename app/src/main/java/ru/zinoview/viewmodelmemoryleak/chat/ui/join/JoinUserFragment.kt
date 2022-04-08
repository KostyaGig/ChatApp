package ru.zinoview.viewmodelmemoryleak.chat.ui.join

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import io.socket.client.IO
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.abstract_ex.AbstractFragment
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.Json
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.SocketConnection
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.join.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.join.JoinUserRepository
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.ChatFragment
import ru.zinoview.viewmodelmemoryleak.databinding.JoinFragmentBinding

class JoinUserFragment : AbstractFragment<JoinUserViewModel.Base,JoinFragmentBinding>(
    JoinUserViewModel.Base::class
) {

    override fun factory(): ViewModelProvider.Factory
        = JoinUserViewModelFactory.Base(
        JoinUserRepository.Base(
            IdSharedPreferences.Base(
                SharedPreferencesReader.Base(),
                requireContext()
            ),
            CloudDataSource.Base(
                IO.socket("http://10.0.2.2:3000"),
                SocketConnection.Base(),
                Json.Base()
            )
        ),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.joinBtn.setOnClickListener {
            val nickname = binding.nicknameField.text.toString().trim()
            viewModel.join(nickname)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.observe(this) {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ChatFragment())
                .commit()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.clean()
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): JoinFragmentBinding
        = JoinFragmentBinding.inflate(inflater,container,false)
}