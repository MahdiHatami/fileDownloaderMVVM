package com.metis.downloader.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "file_table")
data class CustomFile constructor(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "file_id") var id: Int,
  @ColumnInfo(name = "file_title") var title: String,
  @ColumnInfo(name = "file_url") var url: String,
  @ColumnInfo(name = "file_path") var path: String,
  @ColumnInfo(name = "file_progress") var progress: Int,
  @ColumnInfo(name = "file_status") var status: Boolean
) {
  constructor() : this(id = 0, title = "", url = "", path = "", progress = 0, status = false)
}
