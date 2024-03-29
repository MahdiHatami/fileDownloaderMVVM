package com.metis.downloader.file

import android.content.Context
import androidx.work.Data
import androidx.work.Data.Builder
import androidx.work.ListenableWorker.Result
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.metis.downloader.file.Const.DOWNLOAD_DIR
import java.io.File
import java.io.FileOutputStream
import java.net.URL

/**
 * {@Link WorkManager} tarafından sağlanan bir arka plan iş parçacığında
 * eşzamanlı olarak iş yapan bir sınıf.
 * videonun indirme yuzdesini gostermek icin ise [LiveDataHelper] kullanilir.
 *
 * input param:  video url.
 * output param: video dosyasinin kaydedildigi storage path gonderilir.
 */
class DownloadWorker(context: Context, workerParams: WorkerParameters) :
  Worker(context, workerParams) {
  private val PATH = applicationContext.filesDir.path + DOWNLOAD_DIR
  private val liveDataHelper: LiveDataHelper = LiveDataHelper()
  private var outputData: Data? = null
  override fun doWork(): Result {
    try {
      val videoUrl = inputData.getString(Const.INPUT_URL)
      val id = inputData.getString(Const.INPUT_ID)
      val videoName = inputData.getString(Const.INPUT_NAME) + id + EXT
      val file = File(PATH, videoName)
      val fileOutput = FileOutputStream(file)
      val u = URL(videoUrl)
      val urlConnection = u.openConnection()
      urlConnection.connect()
      val lengthOfFile = urlConnection.contentLength
      val inputStream = urlConnection.getInputStream()
      val buffer = ByteArray(1024)
      var bufferLength: Int
      var total: Long = 0
      var percent = 0
      while (inputStream.read(buffer).also { bufferLength = it } > 0) {
        fileOutput.write(buffer, 0, bufferLength)
        total += bufferLength.toLong()
        percent = (total * 100 / lengthOfFile).toInt()
        liveDataHelper.updatePercentage(id!!, percent)
      }
      outputData = createOutputData(percent, file.absolutePath)
      fileOutput.close()
    } catch (e: Exception) {
      e.printStackTrace()
      return Result.failure(outputData!!)
    }
    return Result.success(outputData!!)
  }

  private fun createOutputData(percent: Int, path: String): Data {
    return Builder().putInt(Const.OUTPUT_PERCENT, percent)
      .putString(Const.OUTPUT_FILE_PATH, path)
      .build()
  }

  companion object {
    private const val EXT = ".mp4"
  }

}
