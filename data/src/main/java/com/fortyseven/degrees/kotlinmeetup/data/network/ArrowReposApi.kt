package com.fortyseven.degrees.kotlinmeetup.data.network

import com.fortyseven.degrees.kotlinmeetup.data.model.RepoNw
import retrofit2.http.GET
import retrofit2.http.Path

interface ArrowReposApi {
    @GET("users/arrow-kt/repos?")
    suspend fun getArrowRepos(): List<RepoNw>

    @GET("repos/arrow-kt/{repo_name}")
    suspend fun getArrowRepo(@Path(value = "repo_name", encoded = true) repoName: String): RepoNw
}
