package ru.zinoview.viewmodelmemoryleak.chat.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import io.socket.client.IO
import ru.zinoview.viewmodelmemoryleak.abstract_ex.AbstractFragment
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.Connect
import ru.zinoview.viewmodelmemoryleak.chat.ui.ChatAdapter
import ru.zinoview.viewmodelmemoryleak.chat.ui.ChatMessageDiffUtil
import ru.zinoview.viewmodelmemoryleak.databinding.ChatFragmentBinding
import kotlin.math.log

class ChatFragment : AbstractFragment<ChatViewModel.Base,ChatFragmentBinding>(
    ChatViewModel.Base::class
) {

    private var adapter: ChatAdapter = ChatAdapter.Empty

    override fun factory(): ViewModelProvider.Factory
        = ChatViewModelFactory.Base(
                CloudDataSource.SendMessage.Base(
                    IO.socket("http://10.0.2.2:3000"),
                    Connect.Base(),
                    IdSharedPreferences.Base(
                        SharedPreferencesReader.Base(),
                        requireContext()
                    )
                )
            )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val diffUtil = ChatMessageDiffUtil()
        adapter = ChatAdapter(diffUtil)

        binding.chatRv.adapter = adapter

        binding.sendMessageBtn.setOnClickListener {
            val message = binding.messageField.text.toString().trim()
            viewModel.sendMessage(message)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.observeMessages { messages ->
            adapter.submitList(ArrayList(messages))
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.disconnect()
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): ChatFragmentBinding
        = ChatFragmentBinding.inflate(layoutInflater,container,false)
}