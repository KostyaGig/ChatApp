package ru.zinoview.viewmodelmemoryleak.ui.join

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.databinding.JoinFragmentBinding
import ru.zinoview.viewmodelmemoryleak.ui.chat.NetworkConnectionReceiver
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBar
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper
import ru.zinoview.viewmodelmemoryleak.ui.connection.ConnectionViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.ActivityLauncher
import ru.zinoview.viewmodelmemoryleak.ui.core.LauncherType
import ru.zinoview.viewmodelmemoryleak.ui.core.Text
import ru.zinoview.viewmodelmemoryleak.ui.core.ToolbarActivity
import ru.zinoview.viewmodelmemoryleak.ui.core.koin_scope.ScreenScope
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.Navigation
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.SaveUiStateFragment
import ru.zinoview.viewmodelmemoryleak.ui.join.ui_state.ImageUiState
import ru.zinoview.viewmodelmemoryleak.ui.join.ui_state.UiJoinState
import ru.zinoview.viewmodelmemoryleak.ui.join.ui_state.JoinUiStateViewModel
import ru.zinoview.viewmodelmemoryleak.ui.join.ui_state.UiTextStateTextWatcher
import java.io.ByteArrayOutputStream

class JoinUserFragment : SaveUiStateFragment<JoinUserViewModel.Base, JoinUiStateViewModel ,JoinFragmentBinding>(
    JoinUserViewModel.Base::class,JoinUiStateViewModel::class
), ImageResult {

    private var imageProfile: ImageProfile = ImageProfile.Empty

    private val uiState  = UiJoinState.Base()

    private val text = getKoin().get<Text>()
    private val resourceProvider = getKoin().get<ResourceProvider>()

    private val connectionViewModel by lazy {
        get<ConnectionViewModel.Base>()
    }

    private var userProfileImageState: UserProfileImageState  = UserProfileImageState.UnChosen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiStateViewModel.read(Unit)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkConnectionReceiver = NetworkConnectionReceiver.Base(connectionViewModel)

        binding.rotateBtn.setOnClickListener {
            val state = requireActivity().requestedOrientation
            if (state == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else {
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        }

        binding.joinBtn.setOnClickListener {
            val nickName = text.text(binding.nicknameField)
            viewModel.joinUser(
                imageProfile,
                nickName
            )
        }

        val textWatcher = UiTextStateTextWatcher.Base(uiStateViewModel,text)
        binding.nicknameField.addTextChangedListener(textWatcher)

        binding.profileImage.setOnClickListener {
            (requireActivity() as ActivityLauncher).launch(LauncherType.Image)
        }

        connectionViewModel.connection()
    }

    override fun onStart() {
        super.onStart()

        viewModel.observe(this) { uiJoin ->
            uiJoin.navigate(requireActivity() as Navigation)
            uiJoin.showError(SnackBar.Base(binding.joinBtn, SnackBar.SnackBarVisibility.Empty()))
        }

        connectionViewModel.observe(this) { connection ->
            connection.changeTitle(requireActivity() as ToolbarActivity)
        }

        val imageUiState = ImageUiState.Base()
        uiStateViewModel.observe(this) { uiState ->
            imageProfile = imageUiState.imageProfile(uiState)

            val view = listOf(
                ViewWrapper.Image(binding.profileImage,resourceProvider),
                ViewWrapper.Text(binding.nicknameField)
            )

            userProfileImageState.map(Pair(uiState,view))
        }
    }

    override fun onImageResult(uri: Uri) {
        userProfileImageState = UserProfileImageState.Chosen

        binding.profileImage.setImageURI(uri)

        imageProfile = ImageProfile.Uri(uri)

        uiState.add(imageProfile)
        uiState.save(uiStateViewModel)
    }

    override fun back(navigation: Navigation) = navigation.exit()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): JoinFragmentBinding =
        JoinFragmentBinding.inflate(inflater, container, false)

    override fun koinScopes() = listOf(ScreenScope.Join(), ScreenScope.Connection())
    override fun cleans() = listOf(viewModel)
    override fun recoverStateAfterLaunch() = false
}