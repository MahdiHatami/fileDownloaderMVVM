package com.metis.downloader.file

import androidx.lifecycle.ViewModel
import com.metis.downloader.di.ViewModelBuilder
import com.metis.downloader.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FileModule {

  @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
  internal abstract fun filesFragment(): FilesFragment

  @Binds
  @IntoMap
  @ViewModelKey(FilesViewModel::class)
  abstract fun bindFilesViewModel(viewModel: FilesViewModel): ViewModel
}