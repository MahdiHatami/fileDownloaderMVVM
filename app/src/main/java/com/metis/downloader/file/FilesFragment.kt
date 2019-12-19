package com.metis.downloader.file

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.metis.downloader.EventObserver
import com.metis.downloader.R
import com.metis.downloader.databinding.FileFragmentBinding
import com.metis.downloader.util.permission.PermissionManager
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class FilesFragment : DaggerFragment() {

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var binding: FileFragmentBinding
  private val viewModel by viewModels<FilesViewModel> { viewModelFactory }
  private lateinit var listAdapter: FilesAdapter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.file_fragment, container, false)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this
    setHasOptionsMenu(true)
    return binding.root
  }

  override fun onOptionsItemSelected(item: MenuItem) =
    when (item.itemId) {
      R.id.menu_add -> {
        viewModel.addNewFile()
        true
      }
      else -> false
    }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.files_fragment_menu, menu)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    setupListAdapter()

    viewModel.openFileEvent.observe(viewLifecycleOwner, EventObserver {
      val action = FilesFragmentDirections.actionFileFragmentToPlayerFragment(it)
      findNavController().navigate(action)
    })

    viewModel.items.observe(viewLifecycleOwner) { videos ->
      listAdapter.submitList(videos)
    }

    viewModel.requestStoragePermissionEvent.observe(viewLifecycleOwner, EventObserver {
      requestPermissions(
        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
        REQUEST_CODE_STORAGE
      )
    })

  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    when (requestCode) {
      REQUEST_CODE_STORAGE -> {
        if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
          viewModel.setExternalStorageStatus(
            PermissionManager().getPermissionStatus(
              requireActivity(),
              Manifest.permission.READ_EXTERNAL_STORAGE
            )
          )
          viewModel.addNewFile()
        } else {
          Snackbar.make(requireView(), "Permission denied", Snackbar.LENGTH_LONG).show()
        }
      }
      else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
  }

  private fun setupListAdapter() {
    val viewModel = binding.viewModel
    if (viewModel != null) {
      listAdapter = FilesAdapter(viewModel)
      binding.filesList.adapter = listAdapter
    } else {
      Timber.w("ViewModel not initialized when attempting to set up adapter.")
    }
  }

  companion object {
    private const val REQUEST_CODE_STORAGE: Int = 100
  }
}
