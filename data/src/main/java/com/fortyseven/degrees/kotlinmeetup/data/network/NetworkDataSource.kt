package com.fortyseven.degrees.kotlinmeetup.data.network

import com.fortyseven.degrees.kotlinmeetup.data.model.toDatabaseModel
import repos.RepoDb

interface NetworkDataSource {

    suspend fun getArrowRepos(): List<RepoDb>

    suspend fun getArrowRepo(repoName: String): RepoDb

    companion object {

        operator fun invoke(client: ArrowReposApi) = object : NetworkDataSource {

            override suspend fun getArrowRepos(): List<RepoDb> =
                client.getArrowRepos().map { it.toDatabaseModel() }

            override suspend fun getArrowRepo(repoName: String): RepoDb =
                client.getArrowRepo(repoName).toDatabaseModel()
        }
    }
}
