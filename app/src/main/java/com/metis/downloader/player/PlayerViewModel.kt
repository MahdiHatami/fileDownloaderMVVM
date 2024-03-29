package com.metis.downloader.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.metis.downloader.Event

class PlayerViewModel : ViewModel() {
  private var fileId = ""

  private val _isVideoPathAvailable = MutableLiveData< Event<String>>()
  val isVideoPathAvailable: LiveData<Event<String>> = _isVideoPathAvailable

  fun setCurrentFileId(id: String) {
    fileId = id
  }
}
