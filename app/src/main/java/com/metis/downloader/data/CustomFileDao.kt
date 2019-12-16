package com.metis.downloader.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.NotNull

@Dao
interface CustomFileDao {
  @Query("SELECT * from file_table ORDER BY file_id ASC")
  fun getFiles(): Flow<List<CustomFile>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(file: CustomFile)

  @Query("SELECT * from file_table WHERE file_id=:fileId")
  suspend fun getFileById(@NotNull fileId: Int)

  @Query("DELETE from file_table WHERE file_id=:fileId")
  suspend fun deleteFileById(@NotNull fileId: Int)
}
