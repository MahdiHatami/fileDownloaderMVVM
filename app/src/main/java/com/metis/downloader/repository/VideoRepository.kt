package com.metis.downloader.repository

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.metis.downloader.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.Okio
import okio.buffer
import okio.sink
import java.io.File

private const val URL_BASE = "https://commonsware.com/presos/"
private val PROJECTION = arrayOf(MediaStore.Video.Media._ID)
private const val QUERY = MediaStore.Video.Media.DISPLAY_NAME + " = ?"
private const val AUTHORITY = "${BuildConfig.APPLICATION_ID}.provider"

class VideoRepository(private val context: Context) {
  private val ok = OkHttpClient()
  private val collection =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) MediaStore.Video.Media.getContentUri(
      MediaStore.VOLUME_EXTERNAL
    ) else MediaStore.Video.Media.EXTERNAL_CONTENT_URI

  suspend fun getLocalUri(filename: String): Uri? =
    withContext(Dispatchers.IO) {
      val resolver = context.contentResolver

      resolver.query(collection, PROJECTION, QUERY, arrayOf(filename), null)
        ?.use { cursor ->
          if (cursor.count > 0) {
            cursor.moveToFirst()
            return@withContext ContentUris.withAppendedId(
              collection,
              cursor.getLong(0)
            )
          }
        }

      null
    }

  suspend fun download(filename: String): Uri =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
      downloadQ(filename)
    else
      downloadLegacy(filename)

  @RequiresApi(VERSION_CODES.Q)
  private suspend fun downloadQ(filename: String): Uri =
    withContext(Dispatchers.IO) {
      val url = URL_BASE + filename
      val response = ok.newCall(Request.Builder().url(url).build()).execute()

      if (response.isSuccessful) {
        val values = ContentValues().apply {
          put(MediaStore.Video.Media.DISPLAY_NAME, filename)
          put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/ConferenceVideos")
          put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
          put(MediaStore.Video.Media.IS_PENDING, 1)
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(collection, values)

        uri?.let {
          resolver.openOutputStream(uri)?.use { outputStream ->
            val sink = outputStream.sink().buffer()

            response.body?.source()?.let { sink.writeAll(it) }
            sink.close()
          }

          values.clear()
          values.put(MediaStore.Video.Media.IS_PENDING, 0)
          resolver.update(uri, values, null, null)
        } ?: throw RuntimeException("MediaStore failed for some reason")

        uri
      } else {
        throw RuntimeException("OkHttp failed for some reason")
      }
    }

  private suspend fun downloadLegacy(filename: String): Uri =
    withContext(Dispatchers.IO) {
      val file = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
        filename
      )
      val url = URL_BASE + filename
      val response = ok.newCall(Request.Builder().url(url).build()).execute()

      if (response.isSuccessful) {
        val sink = file.sink().buffer()

        response.body?.source()?.let { sink.writeAll(it) }
        sink.close()

        MediaScannerConnection.scanFile(
          context,
          arrayOf(file.absolutePath),
          arrayOf("video/mp4"),
          null
        )

        FileProvider.getUriForFile(context, AUTHORITY, file)
      } else {
        throw RuntimeException("OkHttp failed for some reason")
      }
    }
}
