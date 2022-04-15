package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import io.socket.client.IO
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.ActivityConnection
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Json
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.SocketConnection
import ru.zinoview.viewmodelmemoryleak.data.cache.Id
import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.data.chat.CloudToDataMessageMapper
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.Data
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.MessagesStore
import ru.zinoview.viewmodelmemoryleak.data.connection.CloudToDataConnectionMapper
import ru.zinoview.viewmodelmemoryleak.data.connection.ConnectionRepository
import ru.zinoview.viewmodelmemoryleak.data.connection.cloud.ConnectionState
import ru.zinoview.viewmodelmemoryleak.databinding.ChatFragmentBinding
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.EditMessageListener
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.UiToEditChatMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBar
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBarHeight
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper

import ru.zinoview.viewmodelmemoryleak.ui.core.AbstractFragment
import ru.zinoview.viewmodelmemoryleak.ui.core.ToolbarActivity
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.Navigation


class ChatFragment : AbstractFragment<ChatViewModel.Base, ChatFragmentBinding>(
    ChatViewModel.Base::class
) {

    private var adapter: ChatAdapter = ChatAdapter.Empty
    private var networkConnectionReceiver: NetworkConnectionReceiver = NetworkConnectionReceiver.Empty

    override fun factory(): ViewModelProvider.Factory {
        val id = Id.Base()
        val prefs = IdSharedPreferences.Base(
            SharedPreferencesReader.Base(id),
            id,
            requireActivity().applicationContext
        )
        val socket = IO.socket("http://10.0.2.2:3000")
        val connection = SocketConnection.Base(
            ActivityConnection.Base()
        )
        val resourceProvider = ru.zinoview.viewmodelmemoryleak.core.ResourceProvider.Base(
            requireActivity().applicationContext
        )
        return ChatViewModelFactory.Base(
            ru.zinoview.viewmodelmemoryleak.data.chat.ChatRepository.Base(
                CloudDataSource.Base(
                    socket,
                    connection,
                    Json.Base(),
                    Gson(),
                    Data.CloudMessage(),
                    MessagesStore.Base()
                ),
                CloudToDataMessageMapper(
                    prefs
                ),
                prefs
            ),
        ConnectionRepository.Base(
            CloudToDataConnectionMapper(),
            ru.zinoview.viewmodelmemoryleak.data.connection.cloud.CloudDataSource.Base(
                socket, connection, ConnectionState.Base(
                    socket,connection,resourceProvider
                ),resourceProvider
            )
        ))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkConnectionReceiver = NetworkConnectionReceiver.Base(viewModel)

        val diffUtil = ChatMessageDiffUtil()
        val editContainer = ViewWrapper.Base(binding.editMessageContainer)

        val snackBar = SnackBar.EmptyField(
            binding.messageField, SnackBar.SnackBarVisibility(
                binding.writeMessageContainer,
                SnackBarHeight.Base()
            )
        )

        val messageSession = MessageSession.Base(
            editContainer,
            ViewWrapper.EditText(
                binding.messageField
            ),
            snackBar
        )

        adapter = ChatAdapter(diffUtil, object : EditMessageListener {
            override fun edit(message: UiChatMessage) {
                message.show(
                    ViewWrapper.Text(
                        binding.oldMessageTv
                    )
                )
                messageSession.show(Unit)
                val editMessage = message.map(UiToEditChatMessageMapper())
                messageSession.addMessage(editMessage)
            }
        })
        binding.chatRv.adapter = adapter

        binding.cancelEditBtn.setOnClickListener {
            messageSession.disconnect(Unit)
        }

        binding.sendMessageBtn.setOnClickListener {
            val message = binding.messageField.text.toString().trim()
            messageSession.sendMessage(viewModel,message)
        }

        viewModel.messages()
        viewModel.connection()
    }

    override fun onStart() {
        super.onStart()
        networkConnectionReceiver.register(requireActivity().applicationContext)

        viewModel.observe(this) { messages ->
            messages.last().changeTitle(requireActivity() as ToolbarActivity)
            adapter.submitList(messages)
        }

        viewModel.observeConnection(this) { connection ->
            connection.changeTitle(requireActivity() as ToolbarActivity)
            connection.showError(adapter)
            connection.messages(viewModel)
        }


    }

    override fun onPause() {
        super.onPause()
        viewModel.clean()
        networkConnectionReceiver.unRegister(requireActivity().applicationContext)
    }

    override fun back(navigation: Navigation) = navigation.exit()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): ChatFragmentBinding
        = ChatFragmentBinding.inflate(layoutInflater,container,false)
}