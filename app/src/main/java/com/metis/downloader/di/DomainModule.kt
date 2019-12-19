package com.metis.downloader.di

import com.metis.downloader.repository.DefaultFileRepository
import com.metis.downloader.repository.FileRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DomainModule {

  @Singleton
  @Binds
  abstract fun bindFileRepository(repo: DefaultFileRepository): FileRepository
}