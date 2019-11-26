package com.metis.downloader.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "file_table")
data class CustomFile(
  @PrimaryKey(autoGenerate = true)
  var id: Int = 0,

  @NonNull
  var title: String = "",

  @NonNull
  var url: String = "",

  var path: String = "",

  var progress: Int = 0,

  var status: Boolean = false
)
