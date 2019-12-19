package com.metis.downloader.di

import android.content.Context
import androidx.room.Room
import com.metis.downloader.data.FileRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
  companion object {
    const val DB_NAME = "files.db"
  }

//  @Provides
//  @Singleton
//  fun provideDatabase(context: Context) = Room
//    .databaseBuilder(context, FileRoomDatabase::class.java, DB_NAME)
//    .addMigrations()
//    .build()


  // inmemory db
  @Provides
  @Singleton
  fun provideDatabase(context: Context) = Room
    .inMemoryDatabaseBuilder(context, FileRoomDatabase::class.java)
    .addMigrations()
    .build()

  @Provides
  @Singleton
  fun provideVideoFileDao(db: FileRoomDatabase) = db.videoFileDao()

}