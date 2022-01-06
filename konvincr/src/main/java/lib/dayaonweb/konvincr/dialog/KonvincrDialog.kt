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
import lib.dayaonweb.konvincr.databinding.RootDialogBinding

private const val TAG = "KonvincrDialog"

class KonvincrDialog : DialogFragment(){

    private var _binding: RootDialogBinding? = null
    private val binding get() = _binding!!
    private var player: ExoPlayer? = null

    // Required to be passed in dialog
    private lateinit var videoUrl: String

    // Optional
    private var titleText = "Go to settings to enable permissions"
    private var subtitleText =
        "Please grant XYZ permission in the next screen as shown in above video"

    companion object {
        fun newInstance(url: String): KonvincrDialog {
            val dialogFragment = KonvincrDialog()
            dialogFragment.videoUrl = url
            return dialogFragment
        }
    }

    // Accessible functions

    fun setTitleText(text: String) {
        titleText = text
    }

    fun setSubtitleText(text: String) {
        subtitleText = text
    }

    fun prepareMediaItem(url: String? = null) {
        val mediaItem = MediaItem.fromUri(Uri.parse(url ?: videoUrl))
        player?.apply {
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
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
        initPlayer()
        initViews()
        attachListeners()
    }

    private fun attachListeners() {
        binding.toolbar.setNavigationOnClickListener {
            dismiss()
        }
    }

    private fun initViews() {
        binding.tvTitle.text = titleText
    }

    private fun initPlayer() {
        player = ExoPlayer.Builder(requireContext())
            .build()
        prepareMediaItem()
        binding.playerView.player = player
    }


    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}