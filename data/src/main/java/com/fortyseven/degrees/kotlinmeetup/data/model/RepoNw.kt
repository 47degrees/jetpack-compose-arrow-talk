package com.fortyseven.degrees.kotlinmeetup.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDateTime
import repos.RepoDb
import se.ansman.kotshi.JsonSerializable

@JsonClass(generateAdapter = true)
@Parcelize
@JsonSerializable
data class RepoNw(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "full_name")
    val fullName: String,
    @Json(name = "description")
    val description: String? = "",
    @Json(name = "html_url")
    val htmlUrl: String? = "",
    @Json(name = "url")
    val url: String,
    @Json(name = "collaborators_url")
    val collaboratorsUrl: String? = "",
    @Json(name = "contributors_url")
    val contributorsUrl: String,
    @Json(name = "issues_url")
    val issuesUrl: String,
    @Json(name = "pulls_url")
    val pullsUrl: String,
    @Json(name = "updated_at")
    val updatedAt: LocalDateTime,
    @Json(name = "clone_url")
    val cloneUrl: String,
    @Json(name = "stargazers_count")
    val stargazersCount: Int,
    @Json(name = "language")
    val language: String? = "",
    @Json(name = "has_wiki")
    val hasWiki: Boolean,
    @Json(name = "has_pages")
    val hasPages: Boolean,
    @Json(name = "forks")
    val forks: Int,
    @Json(name = "open_issues")
    val openIssues: Int,
    @Json(name = "watchers")
    val watchers: Int,
    @Json(name = "default_branch")
    val defaultBranch: String
) : Parcelable {
    companion object
}

fun RepoNw.toDatabaseModel() =
    RepoDb.Impl(
        id.toLong(),
        name,
        description ?: "",
        language ?: "",
        stargazersCount.toLong(),
        forks.toLong(),
        updatedAt,
        htmlUrl ?: "",
        collaboratorsUrl ?: ""
    )


