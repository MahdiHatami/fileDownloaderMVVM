package com.metis.downloader.repository

import com.metis.downloader.data.VideoFile
import com.metis.downloader.data.VideoFileDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface FileRepository {
  suspend fun allFiles(): List<VideoFile>
  suspend fun insert(file: VideoFile)
  suspend fun getFileById(fileId: String): VideoFile
}

class DefaultFileRepository @Inject constructor(
  private val fileDao: VideoFileDao,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FileRepository {


  override suspend fun allFiles(): List<VideoFile> = withContext(ioDispatcher) {
    fileDao.getFiles()
  }

  override suspend fun insert(file: VideoFile) = withContext(ioDispatcher) {
    fileDao.insert(file)
  }

  override suspend fun getFileById(fileId: String) = withContext(ioDispatcher) {
    TODO()
//        fileDao.getFileById(fileId)
  }
}
