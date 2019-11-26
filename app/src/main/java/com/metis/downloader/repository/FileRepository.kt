package com.metis.downloader.repository

import com.metis.downloader.data.CustomFile
import com.metis.downloader.data.CustomFileDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber


class FileRepository(private val fileDao: CustomFileDao) {

  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO


  suspend fun allFiles(): List<CustomFile> {
    return withContext(ioDispatcher) {
      fileDao.getFiles()
    }
  }

  suspend fun insert(file: CustomFile) {
    withContext(ioDispatcher) {
      fileDao.insert(file)
      Timber.d("" + fileDao.getFiles().size)
    }
  }

  suspend fun deleteAllFiles() {
    withContext(ioDispatcher) {
      fileDao.deleteAllFiles()
    }
  }
}
