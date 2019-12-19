package com.metis.downloader.file

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType.CONNECTED
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequest.Builder
import androidx.work.WorkManager
import com.metis.downloader.Event
import com.metis.downloader.data.VideoFile
import com.metis.downloader.repository.FileRepository
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject


class FilesViewModel @Inject constructor(
  private val repository: FileRepository,
  application: Application
) : AndroidViewModel(application) {

  private var context: Context

  private val _items = MutableLiveData<List<VideoFile>>().apply { value = emptyList() }
  val items: LiveData<List<VideoFile>> = _items

  private val _openFileEvent = MutableLiveData<Event<String>>()
  val openFileEvent: LiveData<Event<String>> = _openFileEvent

  val empty: LiveData<Boolean> = Transformations.map(_items) {
    it?.isEmpty() ?: false
  }

  init {
    Timber.d("init files view model")
    context = application
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
    val file = generateRandomFile()
    repository.insert(file)

    startDownloader(file)

    loadFiles()
  }

  private fun startDownloader(videoFile: VideoFile) {
    val oneTimeWorkRequest: OneTimeWorkRequest = createDownloadRequest(videoFile)
    val workManager = WorkManager.getInstance(context)
    workManager.enqueue(oneTimeWorkRequest)
  }

  @NotNull
  private fun createDownloadRequest(videoFile: VideoFile): OneTimeWorkRequest {
    val constraints = Constraints.Builder()
      .setRequiredNetworkType(CONNECTED)
      .build()

    val builder = Data.Builder()
    builder.putString(Const.INPUT_NAME, videoFile.videoTitle)
    builder.putString(Const.INPUT_ID, videoFile.videoId)
    builder.build()

    return Builder(DownloadWorker::class.java).setInputData(builder.build())
      .setConstraints(constraints)
      .build()
  }

  private fun generateRandomFile(): VideoFile {

    val videoFile = VideoFile(
      videoId = UUID.randomUUID().toString(),
      videoTitle = "title one",
      videoUrl = Const.videoLinks[Const.getRandomBetweenRange()],
      videoThumb = Const.imageLinks[Const.getRandomBetweenRange()]
    )
    Timber.d("$videoFile")
    return videoFile
  }
}
