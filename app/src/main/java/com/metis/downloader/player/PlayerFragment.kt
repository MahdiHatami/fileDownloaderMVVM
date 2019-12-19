package com.metis.downloader.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.metis.downloader.EventObserver
import com.metis.downloader.R
import com.metis.downloader.databinding.PlayerFragmentBinding

class PlayerFragment : Fragment() {

  private lateinit var binding: PlayerFragmentBinding
  private val viewModel by lazy { ViewModelProvider(this).get(PlayerViewModel::class.java) }
  private val args: PlayerFragmentArgs by navArgs()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.player_fragment, container, false)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.isVideoPathAvailable.observe(viewLifecycleOwner, EventObserver { videoPath ->
      val mediaController = MediaController(activity)
      mediaController.setAnchorView(binding.videoView)
      binding.videoView.setMediaController(mediaController)
      binding.videoView.setVideoPath(videoPath)
      binding.videoView.requestFocus()
      binding.videoView.start()
    })
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val fileId = args.fileId
    viewModel.setCurrentFileId(fileId)
    findNavController().currentDestination?.label = fileId
  }

}
