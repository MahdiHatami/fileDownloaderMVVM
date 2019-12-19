package com.metis.downloader.file

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metis.downloader.Event
import com.metis.downloader.data.VideoFile
import com.metis.downloader.repository.FileRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

class FilesViewModel @Inject constructor(
  private val repository: FileRepository
) : ViewModel() {

  private val _items = MutableLiveData<List<VideoFile>>().apply { value = emptyList() }
  val items: LiveData<List<VideoFile>> = _items

  private val _openFileEvent = MutableLiveData<Event<String>>()
  val openFileEvent: LiveData<Event<String>> = _openFileEvent

  val empty: LiveData<Boolean> = Transformations.map(_items) {
    it?.isEmpty() ?: false
  }

  init {
    Timber.d("init files view model")
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
