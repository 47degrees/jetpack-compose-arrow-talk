package com.fortyseven.degrees.kotlinmeetup.data.database

import com.fortyseven.degrees.kotlinmeetup.data.Database
import repos.RepoDb

interface DatabaseDataSource {

    suspend fun selectAll(): List<RepoDb>

    suspend fun selectById(id: Long): RepoDb?

    suspend fun selectByName(name: String): RepoDb?

    suspend fun insert(repo: RepoDb)

    suspend fun insertAll(repositories: List<RepoDb>)

    companion object {
        operator fun invoke(db: Database) = object : DatabaseDataSource {

            override suspend fun selectAll(): List<RepoDb> =
                db.repoDbQueries.selectAll().executeAsList()

            override suspend fun selectById(id: Long): RepoDb? =
                db.repoDbQueries.selectById(id).executeAsOneOrNull()

            override suspend fun selectByName(name: String): RepoDb? =
                db.repoDbQueries.selectByName(name).executeAsOneOrNull()

            override suspend fun insert(repo: RepoDb) =
                db.repoDbQueries.insertOrReplace(repo)

            override suspend fun insertAll(repositories: List<RepoDb>) =
                db.transaction {
                    repositories.map {
                        db.repoDbQueries.insertOrReplace(it)
                    }
                }
        }
    }
}
