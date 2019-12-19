package com.metis.downloader.di

import com.metis.downloader.util.DefaultErrorFactory
import com.tuga.konum.util.ErrorFactory
import dagger.Binds
import dagger.Module

@Module
abstract class BaseModule {

  @Binds
  internal abstract fun provideErrorFactory(defaultErrorFactory: DefaultErrorFactory): ErrorFactory
}
