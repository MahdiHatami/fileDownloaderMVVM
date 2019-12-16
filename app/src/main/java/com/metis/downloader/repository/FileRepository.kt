package com.metis.downloader.repository

import com.metis.downloader.data.CustomFile
import com.metis.downloader.data.CustomFileDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FileRepository(private val fileDao: CustomFileDao) {

  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

  suspend fun allFiles(): Flow<List<CustomFile>> {
    return withContext(ioDispatcher) {
      fileDao.getFiles()
    }
  }

  suspend fun insert(file: CustomFile) {
    withContext(ioDispatcher) {
      fileDao.insert(file)
    }
  }

  suspend fun getFileById(fileId: Int) = withContext(ioDispatcher) {
    fileDao.getFileById(fileId)
  }
}
