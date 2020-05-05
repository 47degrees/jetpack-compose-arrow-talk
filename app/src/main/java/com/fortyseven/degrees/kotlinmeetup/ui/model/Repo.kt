package com.fortyseven.degrees.kotlinmeetup.ui.model

import org.threeten.bp.LocalDateTime
import repos.RepoDb

/**
 * Created by danieh on 02/05/2020
 */
data class Repo(
    val id: Long,
    val name: String,
    val description: String,
    val programming_language: String,
    val stargazers: Long,
    val forks: Long,
    val updated_at: LocalDateTime,
    val html_url: String,
    val collaborators_url: String
) {
    override fun toString() = "[$id, $name]\n"

    companion object {
        val repoTest = Repo(
            217378939,
            "arrow-meta",
            "Functional companion to Kotlin's Compiler",
            "Kotlin",
            156,
            24,
            LocalDateTime.now(),
            "",
            ""
        )
    }
}

fun RepoDb.toRepo() =
    Repo(
        id,
        name,
        description,
        programming_language,
        stargazers,
        forks,
        updated_at,
        html_url,
        collaborators_url
    )
