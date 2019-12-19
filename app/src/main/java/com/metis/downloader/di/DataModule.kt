package com.metis.downloader.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.metis.downloader.BuildConfig
import com.metis.downloader.data.FileService
import com.metis.downloader.data.delegatingCallFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.BINARY

@Retention(BINARY)
@Qualifier
private annotation class InternalApi

@Module
object DataModule {

  private const val DEFAULT_TIMEOUT = 60L
  private const val CACHE_SIZE = 10 * 1024 * 1024

  @InternalApi
  @Provides
  @Singleton
  internal fun provideGson(): Gson {
    return GsonBuilder().apply {
      serializeNulls()
      setLenient()
    }.create()
  }

  @InternalApi
  @Provides
  internal fun provideCache(context: Context): Cache {
    return Cache(
      context.cacheDir,
      CACHE_SIZE.toLong()
    )
  }

  @InternalApi
  @Provides
  @Singleton
  internal fun provideOkHttpClient(
    @InternalApi cache: Cache
  ): OkHttpClient {
    return Builder().apply {
      cache(cache)
      connectTimeout(DEFAULT_TIMEOUT, SECONDS)
      readTimeout(DEFAULT_TIMEOUT, SECONDS)
      writeTimeout(DEFAULT_TIMEOUT, SECONDS)
    }.build()
  }

  @Provides
  @Singleton
  internal fun provideRetrofit(
    @InternalApi gson: Gson,
    @InternalApi okHttpClient: Lazy<OkHttpClient>
  ): Retrofit {
    return Retrofit.Builder().apply {
      baseUrl(BuildConfig.SERVER_URL)
      addConverterFactory(GsonConverterFactory.create(gson))
      delegatingCallFactory(okHttpClient)
    }.build()
  }

  @Provides
  @Singleton
  internal fun provideKonumService(retrofit: Retrofit): FileService = retrofit.create()
}
