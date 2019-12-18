package com.metis.downloader.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "video_files")
data class VideoFile(
  @PrimaryKey(autoGenerate = true)
  var videoId: Int = 0,
  var videoTitle: String
)

