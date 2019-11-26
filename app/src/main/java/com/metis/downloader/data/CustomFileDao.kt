package com.metis.downloader.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CustomFileDao {
  @Query("SELECT * from file_table ORDER BY id ASC")
  fun getFiles(): List<CustomFile>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(file: CustomFile)

  @Query("DELETE from file_table WHERE id=:fileId")
  suspend fun deleteFileById(fileId: Int)

  @Query("DELETE from file_table")
  suspend fun deleteAllFiles()
}
