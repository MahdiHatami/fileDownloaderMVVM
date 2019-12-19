package com.metis.downloader.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "video_files")
data class VideoFile(
  @PrimaryKey
  var videoId: String,
  var videoTitle: String,
  var videoUrl: String = "",
  var videoThumb: String = "",
  var percentage: Int = 0,
  var isDownloaded: Boolean = false
)

