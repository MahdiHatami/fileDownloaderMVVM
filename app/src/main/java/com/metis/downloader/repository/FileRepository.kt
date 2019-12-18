package com.metis.downloader.repository

import com.metis.downloader.data.VideoFile
import com.metis.downloader.data.VideoFileDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FileRepository(private val fileDao: VideoFileDao) {

  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

  suspend fun allFiles(): Flow<List<VideoFile>> = withContext(ioDispatcher) {
    val a = fileDao.getFiles()
    a
  }

  suspend fun insert(file: VideoFile) = withContext(ioDispatcher) {
    fileDao.insert(file)
  }

  suspend fun getFileById(fileId: Int) = withContext(ioDispatcher) {
    //    fileDao.getFileById(fileId)
  }
}
