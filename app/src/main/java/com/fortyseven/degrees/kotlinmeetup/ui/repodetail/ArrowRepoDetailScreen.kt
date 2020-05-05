package com.fortyseven.degrees.kotlinmeetup.ui.repodetail

import androidx.compose.Composable
import androidx.lifecycle.LiveData
import androidx.ui.res.stringResource
import com.fortyseven.degrees.kotlinmeetup.R
import com.fortyseven.degrees.kotlinmeetup.ui.ArrowRepoState
import com.fortyseven.degrees.kotlinmeetup.ui.composables.ErrorView
import com.fortyseven.degrees.kotlinmeetup.ui.composables.LoadingView
import com.fortyseven.degrees.kotlinmeetup.ui.composables.exhaustive
import com.fortyseven.degrees.kotlinmeetup.ui.composables.observe
import com.fortyseven.degrees.kotlinmeetup.ui.failure.ArrowRepoDetailFailure
import com.fortyseven.degrees.kotlinmeetup.ui.model.Repo
import com.fortyseven.degrees.kotlinmeetup.ui.repos.RepoView

@Composable
fun ArrowRepoDetailScreen(
    state: LiveData<ArrowRepoState<ArrowRepoDetailFailure, Repo>>
) = state.observe?.let { arrowRepoDetailState: ArrowRepoState<ArrowRepoDetailFailure, Repo> ->
    when (arrowRepoDetailState) {
        is ArrowRepoState.Loading -> LoadingView()
        is ArrowRepoState.Data -> RepoView(arrowRepoDetailState.data)
        is ArrowRepoState.Error -> ErrorView(
            when (arrowRepoDetailState.error) {
                is ArrowRepoDetailFailure.NoRepositoryFound -> stringResource(R.string.errors_repository_not_found)
                is ArrowRepoDetailFailure.RepositoryError -> arrowRepoDetailState.error.throwable.message
                    ?: stringResource(R.string.errors_unknown)
            }.exhaustive
        )
    }.exhaustive
}
