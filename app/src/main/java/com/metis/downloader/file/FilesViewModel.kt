package com.metis.downloader.file

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.metis.downloader.Event
import com.metis.downloader.data.CustomFile

class FilesViewModel : ViewModel() {
  private val _items = MutableLiveData<List<CustomFile>>().apply { value = emptyList() }
  val items: LiveData<List<CustomFile>> = _items

  private val _openFileEvent = MutableLiveData<Event<String>>()
  val openFileEvent: LiveData<Event<String>> = _openFileEvent

  val empty: LiveData<Boolean> = Transformations.map(_items) {
    it.isEmpty()
  }

  /**
   * Called by Data Binding.
   */
  fun openFile(fileId: String) {
    _openFileEvent.value = Event(fileId)
  }

  fun addNewFile() {
    _items.value = listOf(
      CustomFile("1", "title one", "", "")
    )
  }
}
