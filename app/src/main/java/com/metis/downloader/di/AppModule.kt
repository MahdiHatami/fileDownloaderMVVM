package com.metis.downloader.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
object AppModule {

  @Singleton
  @Provides
  fun provideIoDispatcher() = Dispatchers.IO
}
