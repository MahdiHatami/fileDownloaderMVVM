package com.metis.downloader.di

import android.app.Application
import com.metis.downloader.FileApplication
import com.metis.downloader.file.FileModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    AndroidSupportInjectionModule::class,
    AndroidInjectionModule::class,
    BaseModule::class,
    ContextModule::class,
    AppModule::class,
    DataModule::class,
    DomainModule::class,
    DatabaseModule::class,

    // fragments
    FileModule::class
  ]
)
interface AppComponent : AndroidInjector<FileApplication> {
  @Component.Factory
  interface Factory {
    fun create(@BindsInstance application: Application): AppComponent
  }
}