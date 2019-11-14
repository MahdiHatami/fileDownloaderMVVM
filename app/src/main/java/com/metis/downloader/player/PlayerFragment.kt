package com.metis.downloader.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.metis.downloader.R

class PlayerFragment : Fragment() {

  companion object {
    fun newInstance() = PlayerFragment()
  }

  private lateinit var viewModel: PlayerViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.player_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(PlayerViewModel::class.java)
    // TODO: Use the ViewModel
  }

}
