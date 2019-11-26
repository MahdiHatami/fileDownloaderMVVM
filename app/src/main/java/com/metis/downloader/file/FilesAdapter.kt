package com.metis.downloader.file

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.metis.downloader.data.CustomFile
import com.metis.downloader.databinding.FileItemBinding
import com.metis.downloader.file.FilesAdapter.ViewHolder

/**
 * Adapter for the file list. Has a reference to the [FilesViewModel] to send actions back to it.
 */
class FilesAdapter(private val viewModel: FilesViewModel) :
  ListAdapter<CustomFile, ViewHolder>(FileDiffCallback()) {

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = getItem(position)

    holder.bind(viewModel, item)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder.from(parent)
  }

  class ViewHolder private constructor(private val binding: FileItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(viewModel: FilesViewModel, item: CustomFile) {
      binding.viewModel = viewModel
      binding.file = item
      binding.executePendingBindings()
    }

    companion object {
      fun from(parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FileItemBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
      }
    }
  }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class FileDiffCallback : DiffUtil.ItemCallback<CustomFile>() {
  override fun areItemsTheSame(oldItem: CustomFile, newItem: CustomFile): Boolean {
    return oldItem.id == newItem.id
  }

  override fun areContentsTheSame(oldItem: CustomFile, newItem: CustomFile): Boolean {
    return oldItem == newItem
  }
}
