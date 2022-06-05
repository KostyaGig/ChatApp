package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import ru.zinoview.viewmodelmemoryleak.databinding.ChatFragmentBinding
import ru.zinoview.viewmodelmemoryleak.ui.users.BundleUser
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.EditMessageListener
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.ToEditedMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.ToOldMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.ChatUiState
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.ChatUiStateViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.user_status.UserStatusViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBar
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBarHeight
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper
import ru.zinoview.viewmodelmemoryleak.ui.connection.ConnectionViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.Adapter

import ru.zinoview.viewmodelmemoryleak.ui.core.ToolbarActivity
import ru.zinoview.viewmodelmemoryleak.ui.core.koin_scope.ScreenScope
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.Navigation
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.ParcelableWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.SaveUiStateFragment
import ru.zinoview.viewmodelmemoryleak.ui.users.NavigationData

class ChatFragment : SaveUiStateFragment<ChatViewModel.Base, ChatUiStateViewModel,  ChatFragmentBinding>(
    ChatViewModel.Base::class,
    ChatUiStateViewModel::class
) {
    private var adapter: Adapter<List<UiMessage>> = Adapter.Empty()
    private var scrollListener: ChatRecyclerViewScrollListener =
        ChatRecyclerViewScrollListener.Empty

    private var messageSession: MessageSession = MessageSession.Empty

    private val connectionViewModel by lazy { get<ConnectionViewModel.Base>() }

    private val userStatusViewModel by lazy { getKoin().get<UserStatusViewModel.Base>() }
    private val typeMessageTextWatcher by lazy { get<TypeMessageTextWatcher>() }

    private val mapper by lazy { get<UiMessagesKeysMapper>() }

    private var bundleUser: BundleUser = BundleUser.Empty

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userNavigationData = NavigationData.User(ParcelableWrapper.Empty)
        val user = userNavigationData.parcelable<BundleUser.Base>(arguments!!)

        bundleUser = if (user is ParcelableWrapper.Empty) {
            uiStateViewModel.getUser()
        } else {
            userNavigationData.parcelable<BundleUser.Base>(arguments!!).parcelable()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.changeOrientation.setOnClickListener {
            val state = requireActivity().requestedOrientation
            if (state == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else {
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        }

        networkConnectionReceiver = NetworkConnectionReceiver.Base(connectionViewModel)

        val editContainer = ViewWrapper.Base(binding.editMessageContainer)

        binding.messageField.addTextChangedListener(typeMessageTextWatcher)

        val snackBar = SnackBar.EmptyField(
            binding.messageField, SnackBar.SnackBarVisibility(
                binding.writeMessageContainer,
                SnackBarHeight.Base()
            )
        )

        messageSession = MessageSession.Base(
            editContainer,
            ViewWrapper.EditText(
                binding.messageField
            ),
            snackBar,
            ToEditedMessageMapper(
                bundleUser
            ),
            ToOldMessageMapper()
        )

        val diffUtil = ChatMessageDiffUtil()

        adapter = ChatAdapter(diffUtil,EditMessageListener.Base(binding,messageSession), mapper)

        val manager = LinearLayoutManager(requireContext())

        binding.chatRv.layoutManager = manager
        binding.chatRv.adapter = adapter as ChatAdapter


        scrollListener = ChatRecyclerViewScrollListener.Base(manager, viewModel,bundleUser)
        binding.chatRv.addOnScrollListener(scrollListener)

        binding.cancelEditBtn.setOnClickListener {
            messageSession.disconnect(Unit)
            messageSession.saveState(uiStateViewModel)
        }

        binding.sendMessageBtn.setOnClickListener {
            val message = binding.messageField.text.toString().trim()

            bundleUser.sendMessage(viewModel,messageSession,message)
        }

        connectionViewModel.connection()
    }

    override fun onStart() {
        super.onStart()
        userStatusViewModel.online()

        viewModel.observe(this) { messages ->
            Log.d("zinoviewk","message last state ${messages.last()}")
            messages.last().changeTitle(
                Pair(requireActivity() as ToolbarActivity,bundleUser)
            )
            adapter.update(messages)
        }

        viewModel.observeScrollCommunication(this) { uiScroll ->
            uiScroll.scroll(binding.chatRv, scrollListener)
        }

        connectionViewModel.observe(this) { connection ->
            connection.changeTitle(requireActivity() as ToolbarActivity)
            connection.doAction { bundleUser.doAction(viewModel) }
        }

        val editText = ViewWrapper.EditText(binding.messageField)
        val text = ViewWrapper.Text(binding.oldMessageTv)

        uiStateViewModel.observe(this) { states ->
            states.forEach { state ->
                state.recover(editText, text, messageSession, adapter)
            }
        }
    }

    override fun onPause() {
        super.onPause()

        val editTextState = ChatUiState.EditText(binding.messageField.text.toString())
        messageSession.saveState(uiStateViewModel, editTextState,bundleUser)

        viewModel.showProcessingMessages()
        // todo make user offline when he leaves the app
        userStatusViewModel.offline()
        userStatusViewModel.clean()

        uiStateViewModel.saveUser(bundleUser)
    }

    override fun back(navigation: Navigation) = navigation.exit()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): ChatFragmentBinding =
        ChatFragmentBinding.inflate(layoutInflater, container, false)

    override fun koinScopes() = listOf(ScreenScope.Connection(), ScreenScope.Chat())
    override fun cleans() = listOf(connectionViewModel,viewModel)
    override fun recoverStateAfterLaunch() = false
}