package com.metis.downloader.file

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.metis.downloader.Event
import com.metis.downloader.data.CustomFile
import com.metis.downloader.data.FileRoomDatabase
import com.metis.downloader.repository.FileRepository
import kotlinx.coroutines.launch

class FilesViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: FileRepository

  private val _items = MutableLiveData<List<CustomFile>>().apply { value = emptyList() }
  val items: LiveData<List<CustomFile>> = _items

  private val _openFileEvent = MutableLiveData<Event<String>>()
  val openFileEvent: LiveData<Event<String>> = _openFileEvent

  val empty: LiveData<Boolean> = Transformations.map(_items) {
    it.isEmpty()
  }

  init {
    val fileDao = FileRoomDatabase.getDatabase(application).fileDao()
    repository = FileRepository(fileDao)
    loadFiles()
  }

  private fun loadFiles() {
    viewModelScope.launch {
      repository.deleteAllFiles()
      _items.value = repository.allFiles()
    }
  }

  /**
   * Called by Data Binding.
   */
  fun openFile(fileId: String) {
    _openFileEvent.value = Event(fileId)
  }

  fun addNewFile() = viewModelScope.launch {
    repository.insert(generateRandomFile())
  }

  private fun generateRandomFile(): CustomFile {
    return CustomFile(title = "title one", url = "http")
  }
}
