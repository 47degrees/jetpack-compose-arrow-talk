package com.fortyseven.degrees.kotlinmeetup.di

import com.fortyseven.degrees.kotlinmeetup.data.network.ApplicationJsonAdapterFactory
import com.fortyseven.degrees.kotlinmeetup.data.network.ArrowReposApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.LocalDateTime
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by danieh on 02/05/2020
 */
@Module
class NetworkModule(private val url: String, private val debug: Boolean) {

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi.Builder()
        .add(ApplicationJsonAdapterFactory.INSTANCE)
        .add(LocalDateTime::class.java, LocalDateTimeJsonAdapter)
        .build()

    @Provides
    @Singleton
    fun providesMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    internal fun provideOkHttpClient() = OkHttpClient.Builder().apply {
        if (debug) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            addInterceptor(loggingInterceptor)
        }
    }.build()

    @Provides
    @Singleton
    internal fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ) =
        Retrofit.Builder().apply {
            baseUrl(url)
            client(okHttpClient)
            addConverterFactory(moshiConverterFactory)
        }.build()

    @Provides
    @Singleton
    internal fun provideCharactersApi(retrofit: Retrofit): ArrowReposApi =
        retrofit.create(ArrowReposApi::class.java)
}
