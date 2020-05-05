package com.fortyseven.degrees.kotlinmeetup.data.repository

import arrow.fx.IO
import arrow.fx.extensions.io.async.effectMap
import arrow.fx.handleErrorWith
import com.fortyseven.degrees.kotlinmeetup.data.database.DatabaseDataSource
import com.fortyseven.degrees.kotlinmeetup.data.network.NetworkDataSource
import repos.RepoDb

interface ArrowReposRepository {

    fun getArrowRepos(): IO<List<RepoDb>>

    fun getArrowRepo(repoName: String): IO<RepoDb?>

    companion object {

        operator fun invoke(network: NetworkDataSource, db: DatabaseDataSource) =
            object : ArrowReposRepository {

                override fun getArrowRepos(): IO<List<RepoDb>> =
                    IO.effect { network.getArrowRepos() }
                        .effectMap { repos ->
                            db.insertAll(repos)
                        }.effectMap {
                            db.selectAll()
                        }.handleErrorWith {
                            IO.effect { db.selectAll() }
                        }

                override fun getArrowRepo(repoName: String): IO<RepoDb?> =
                    IO.effect { network.getArrowRepo(repoName) }
                        .effectMap { repo: RepoDb ->
                            db.insert(repo)
                            repo
                        }.effectMap { repo: RepoDb ->
                            db.selectById(repo.id)
                        }.handleErrorWith {
                            IO.effect { db.selectByName(repoName) }
                        }
            }
    }
}
