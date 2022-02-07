package com.metis.downloader

import dagger.android.AndroidInjector

class FileApplication : BaseApplication() {
  override fun applicationInjector(): AndroidInjector<out dagger.android.support.DaggerApplication> {
    return DaggerAppComponent.factory().create(this).also { appComponent ->
      appComponent.inject(this@FileApplication)
    }
  }
}
