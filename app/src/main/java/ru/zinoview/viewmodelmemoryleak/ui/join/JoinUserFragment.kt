package ru.zinoview.viewmodelmemoryleak.ui.join

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.ext.android.getKoin
import ru.zinoview.viewmodelmemoryleak.databinding.JoinFragmentBinding
import ru.zinoview.viewmodelmemoryleak.ui.chat.NetworkConnectionReceiver
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBar
import ru.zinoview.viewmodelmemoryleak.ui.core.ResultApiActivity
import ru.zinoview.viewmodelmemoryleak.ui.core.Text
import ru.zinoview.viewmodelmemoryleak.ui.core.ToolbarActivity
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.Navigation
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.NetworkConnectionFragment

class JoinUserFragment : NetworkConnectionFragment<JoinUserViewModel.Base, JoinFragmentBinding>(
    JoinUserViewModel.Base::class
), ImageResult {

    private var imageProfile: ImageProfile = ImageProfile.Empty

    private val text = getKoin().get<Text>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkConnectionReceiver = NetworkConnectionReceiver.Base(viewModel)

        binding.joinBtn.setOnClickListener {
            val nickName = text.text(binding.nicknameField)
            viewModel.joinUser(
                imageProfile,
                nickName
            )
        }

        binding.profileImage.setOnClickListener {
            (requireActivity() as ResultApiActivity).image()
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

    override fun onImageResult(uri: Uri) {
        binding.profileImage.setImageURI(uri)
        imageProfile = ImageProfile.Base(uri)
    }

    override fun back(navigation: Navigation) = navigation.exit()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): JoinFragmentBinding
        = JoinFragmentBinding.inflate(inflater,container,false)

    override fun dependenciesScope() = SCOPE_NAME

    private companion object {
        private const val SCOPE_NAME = "jufScope"
    }
}