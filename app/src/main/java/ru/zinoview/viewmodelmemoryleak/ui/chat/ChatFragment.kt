package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.zinoview.viewmodelmemoryleak.databinding.ChatFragmentBinding
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.EditMessageListener
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.UiToEditChatMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBar
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBarHeight
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper

import ru.zinoview.viewmodelmemoryleak.ui.core.ToolbarActivity
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.Navigation
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.NetworkConnectionFragment


class ChatFragment : NetworkConnectionFragment<ChatViewModel.Base, ChatFragmentBinding>(
    ChatViewModel.Base::class
) {

    private var adapter: ChatAdapter = ChatAdapter.Empty

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

    override fun back(navigation: Navigation) = navigation.exit()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): ChatFragmentBinding
        = ChatFragmentBinding.inflate(layoutInflater,container,false)

    override fun dependenciesScope() = SCOPE_NAME

    private companion object {
        private const val SCOPE_NAME = "cufScope"
    }
}