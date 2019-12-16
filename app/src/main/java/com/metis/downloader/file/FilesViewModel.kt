package com.metis.downloader.file

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.metis.downloader.Event
import com.metis.downloader.data.CustomFile
import com.metis.downloader.data.FileRoomDatabase
import com.metis.downloader.repository.FileRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class FilesViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: FileRepository

  private val _items = MutableLiveData<List<CustomFile>>().apply { value = emptyList() }
  val items: LiveData<List<CustomFile>> = _items

  private val _openFileEvent = MutableLiveData<Event<Int>>()
  val openFileEvent: LiveData<Event<Int>> = _openFileEvent

  val empty: LiveData<Boolean> = Transformations.map(_items) {
    it?.isEmpty() ?: false
  }

  init {
    Timber.d("init files view model")
    val fileDao = FileRoomDatabase.getDatabase(application).fileDao()
    repository = FileRepository(fileDao)
    loadFiles()
  }

  private fun loadFiles() {
    liveData {
      emit(repository.allFiles().asLiveData())
    }
  }

  /**
   * Called by Data Binding.
   */
  fun openFile(fileId: Int) {
    _openFileEvent.value = Event(fileId)
  }

  fun addNewFile() = viewModelScope.launch {
    repository.insert(generateRandomFile())
  }

  private fun generateRandomFile(): CustomFile {
    return CustomFile(title = "title one", url = "http")
  }
}
