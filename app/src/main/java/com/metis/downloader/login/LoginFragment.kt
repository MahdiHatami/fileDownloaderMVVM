package com.metis.downloader.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.metis.downloader.EventObserver
import com.metis.downloader.R
import com.metis.downloader.databinding.LoginFragmentBinding
import com.metis.downloader.setupSnackbar

class LoginFragment : Fragment() {

  private lateinit var binding: LoginFragmentBinding
  private val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    setupSnackbar()

    viewModel.navigateToFiles.observe(viewLifecycleOwner, EventObserver {
      findNavController()
        .navigate(
          LoginFragmentDirections.actionLoginFragmentToFileFragment()
        )
    })
  }

  private fun setupSnackbar(){
    view?.setupSnackbar(this, viewModel.snackbarMessage, Snackbar.LENGTH_SHORT)

  }

}
