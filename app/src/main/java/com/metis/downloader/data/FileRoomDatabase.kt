package com.metis.downloader.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [VideoFile::class], version = 1, exportSchema = false)
abstract class FileRoomDatabase : RoomDatabase() {

  abstract fun videoFileDao(): VideoFileDao

  companion object {
    // Singleton prevents multiple instances of database opening at the
    // same time.
    @Volatile
    private var INSTANCE: FileRoomDatabase? = null

    fun getDatabase(context: Context): FileRoomDatabase {
      val tempInstance = INSTANCE
      if (tempInstance != null) {
        return tempInstance
      }
      synchronized(this) {
        val instance = Room.inMemoryDatabaseBuilder(
          context.applicationContext,
          FileRoomDatabase::class.java
        ).build()
        INSTANCE = instance
        return instance
      }
    }
  }
}
