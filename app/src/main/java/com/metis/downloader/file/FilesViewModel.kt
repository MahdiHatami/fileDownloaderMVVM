package com.metis.downloader.file

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.metis.downloader.Event
import com.metis.downloader.data.FileRoomDatabase
import com.metis.downloader.data.VideoFile
import com.metis.downloader.repository.FileRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID

class FilesViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: FileRepository

  private val _items = MutableLiveData<List<VideoFile>>().apply { value = emptyList() }
  val items: LiveData<List<VideoFile>> = _items

  private val _openFileEvent = MutableLiveData<Event<String>>()
  val openFileEvent: LiveData<Event<String>> = _openFileEvent

  val empty: LiveData<Boolean> = Transformations.map(_items) {
    it?.isEmpty() ?: false
  }

  init {
    Timber.d("init files view model")
    val fileDao = FileRoomDatabase.getDatabase(application).fileDao()
    repository = FileRepository(fileDao)
    loadFiles()
  }

  private fun loadFiles() = viewModelScope.launch {
    _items.postValue(repository.allFiles())
  }

  /**
   * Called by Data Binding.
   */
  fun openFile(fileId: String) {
    _openFileEvent.value = Event(fileId)
  }

  /**
   * called by ui
   */
  fun addNewFile() = viewModelScope.launch {
    repository.insert(generateRandomFile())
    loadFiles()
  }

  private fun generateRandomFile(): VideoFile {
    val videoFile = VideoFile(videoId = UUID.randomUUID().toString(), videoTitle = "title one")
    Timber.d("$videoFile")
    return videoFile
  }
}
