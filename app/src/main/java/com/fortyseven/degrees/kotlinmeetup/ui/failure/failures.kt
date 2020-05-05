package com.fortyseven.degrees.kotlinmeetup.ui.failure

/**
 * Created by danieh on 02/05/2020
 */
sealed class ArrowReposFailure {

    object NoRepositories : ArrowReposFailure()

    class RepositoriesError(val throwable: Throwable) : ArrowReposFailure()
}

sealed class ArrowRepoDetailFailure {

    object NoRepositoryFound : ArrowRepoDetailFailure()

    class RepositoryError(val throwable: Throwable) : ArrowRepoDetailFailure()
}
