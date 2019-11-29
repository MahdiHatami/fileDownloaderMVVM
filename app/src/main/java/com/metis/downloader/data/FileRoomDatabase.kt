package com.metis.downloader.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


// Annotates class to be a Room Database with a table (entity) of the CustomFile class
@Database(entities = [CustomFile::class], version = 1, exportSchema = false)
public abstract class FileRoomDatabase : RoomDatabase() {

  abstract fun fileDao(): CustomFileDao

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
