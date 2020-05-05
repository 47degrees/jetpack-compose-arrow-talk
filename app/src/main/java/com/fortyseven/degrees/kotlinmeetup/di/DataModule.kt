package com.fortyseven.degrees.kotlinmeetup.di

import android.content.Context
import com.fortyseven.degrees.kotlinmeetup.data.Database
import com.fortyseven.degrees.kotlinmeetup.data.database.DatabaseDataSource
import com.fortyseven.degrees.kotlinmeetup.data.network.ArrowReposApi
import com.fortyseven.degrees.kotlinmeetup.data.network.NetworkDataSource
import com.fortyseven.degrees.kotlinmeetup.data.repository.ArrowReposRepository
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import org.threeten.bp.LocalDateTime
import repos.RepoDb
import javax.inject.Singleton

/**
 * Created by danieh on 02/05/2020
 */
@Module
class DataModule(private val context: Context) {

    @Singleton
    @Provides
    internal fun provideArrowReposRepository(
        network: NetworkDataSource,
        db: DatabaseDataSource
    ): ArrowReposRepository =
        ArrowReposRepository.invoke(network, db)

    @Provides
    @Singleton
    internal fun provideNetworkDataSource(client: ArrowReposApi): NetworkDataSource =
        NetworkDataSource.invoke(client)

    @Provides
    @Singleton
    internal fun provideLocalDateTimeColumnAdapter(): ColumnAdapter<LocalDateTime, String> =
        object : ColumnAdapter<LocalDateTime, String> {
            override fun decode(databaseValue: String): LocalDateTime =
                LocalDateTime.parse(databaseValue, LocalDateTimeFormatter)

            override fun encode(value: LocalDateTime): String =
                value.format(LocalDateTimeFormatter)
        }

    @Provides
    @Singleton
    internal fun provideDatabase(localDateTimeColumnAdapter: ColumnAdapter<LocalDateTime, String>): Database =
        context.run {
            Database(
                AndroidSqliteDriver(Database.Schema, this, "kotlinmeetup.db"),
                RepoDb.Adapter(localDateTimeColumnAdapter)
            )
        }

    @Provides
    @Singleton
    internal fun provideDatabaseDataSource(db: Database): DatabaseDataSource =
        DatabaseDataSource.invoke(db)
}
