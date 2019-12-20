package com.metis.downloader.file

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

internal class LiveDataHelper {
  private val hashMap =
    HashMap<String, MediatorLiveData<Int>?>()
  private val percent = MediatorLiveData<Int>()
  fun updatePercentage(id: String, percentage: Int) {
    percent.postValue(percentage)
    hashMap[id] = percent
  }

  fun observePercentage(id: String): LiveData<Int>? {
    return if (hashMap.containsKey(id)) hashMap[id] else percent
  }

  companion object {
    private var liveDataHelper: LiveDataHelper? = null
    @get:Synchronized val instance: LiveDataHelper?
      get() {
        if (liveDataHelper == null) liveDataHelper =
          LiveDataHelper()
        return liveDataHelper
      }
  }
}
