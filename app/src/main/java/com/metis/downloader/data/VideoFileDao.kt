package com.metis.downloader.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoFileDao {
  @Query("SELECT * from video_files ORDER BY videoId ASC")
  fun getFiles(): Flow<List<VideoFile>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(file: VideoFile)

//  @Query("SELECT * from video_files WHERE videoId =:fileId")
//  suspend fun getFileById(fileId: Int)

//  @Query("DELETE from video_files WHERE videoId =:fileId")
//  suspend fun deleteFileById(@NotNull fileId: Int)
}
