package com.metis.downloader.file

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.metis.downloader.EventObserver
import com.metis.downloader.R
import com.metis.downloader.databinding.FileFragmentBinding
import timber.log.Timber

class FilesFragment : Fragment() {

  private lateinit var binding: FileFragmentBinding
  private val viewModel by lazy { ViewModelProvider(this).get(FilesViewModel::class.java) }

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

}
