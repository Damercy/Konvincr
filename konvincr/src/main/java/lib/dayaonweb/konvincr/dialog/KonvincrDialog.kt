package lib.dayaonweb.konvincr.dialog

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.Listener
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.util.RepeatModeUtil
import com.google.android.material.snackbar.Snackbar
import lib.dayaonweb.konvincr.databinding.RootDialogBinding
import android.content.Intent
import android.provider.Settings


private const val TAG = "KonvincrDialog"

class KonvincrDialog : DialogFragment(), Listener {


    private var _binding: RootDialogBinding? = null
    private val binding get() = _binding!!
    private var player: ExoPlayer? = null

    // Required to be passed in dialog
    private lateinit var videoUrl: String

    // Optional
    private var titleText = "Grant permission"
    private var bottomText = "Allow permissions in settings"
    private var buttonText = "Settings"
    private var onButtonClick = {
        openSettingsScreen()
    }

    companion object {
        fun newInstance(url: String): KonvincrDialog {
            val dialogFragment = KonvincrDialog()
            dialogFragment.videoUrl = url
            return dialogFragment
        }
    }

    // Accessible functions

    fun setButtonText(text: String) {
        buttonText = text
    }

    fun setTitleText(text: String) {
        titleText = text
    }

    fun setBottomText(text: String) {
        bottomText = text
    }

    fun setButtonAction(action: () -> Unit) {
        onButtonClick = action
    }


    fun prepareMediaItem(url: String? = null) {
        val mediaItem = MediaItem.fromUri(Uri.parse(url ?: videoUrl))
        player?.apply {
            addListener(this@KonvincrDialog)
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onStart() {
        super.onStart()
        initPlayer()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RootDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        attachListeners()
    }

    private fun attachListeners() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                dismiss()
            }
            btnSettings.setOnClickListener {
                onButtonClick.invoke()
            }
        }
    }

    private fun openSettingsScreen() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.fromParts("package", requireContext().packageName, null)
        }
        startActivity(intent)
    }

    private fun initViews() {
        binding.apply {
            tvTitle.text = bottomText
            btnSettings.text = buttonText
            toolbar.title = titleText
        }
    }

    private fun initPlayer() {
        player = ExoPlayer.Builder(requireContext())
            .build()
        prepareMediaItem()
        binding.playerView.player = player
        binding.playerView.apply {
            controllerShowTimeoutMs = 1000
            setShowPreviousButton(false)
            setShowNextButton(false)
        }
    }

    override fun onPlayerError(error: PlaybackException) {
        val throwable = error.cause
        val errorCode = error.errorCode
        val errorCodename = error.errorCodeName
        val msg = error.message
        Snackbar.make(requireView(), errorCodename, Snackbar.LENGTH_LONG).show()
        Log.e(TAG, "onPlayerError: $errorCode\t$errorCodename\t$msg", throwable)
    }

    override fun onStop() {
        super.onStop()
        binding.playerView.player = null
        player?.release()
        player = null
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}