package com.fortyseven.degrees.kotlinmeetup.ui.repodetail

import androidx.compose.Composable
import androidx.lifecycle.LiveData
import androidx.ui.livedata.observeAsState
import androidx.ui.res.stringResource
import com.fortyseven.degrees.kotlinmeetup.R
import com.fortyseven.degrees.kotlinmeetup.ui.ArrowRepoState
import com.fortyseven.degrees.kotlinmeetup.ui.composables.ErrorView
import com.fortyseven.degrees.kotlinmeetup.ui.composables.LoadingView
import com.fortyseven.degrees.kotlinmeetup.ui.composables.exhaustive
import com.fortyseven.degrees.kotlinmeetup.ui.failure.ArrowRepoDetailFailure
import com.fortyseven.degrees.kotlinmeetup.ui.model.Repo
import com.fortyseven.degrees.kotlinmeetup.ui.repos.RepoView

@Composable
fun ArrowRepoDetailScreen(
    liveData: LiveData<ArrowRepoState<ArrowRepoDetailFailure, Repo>>
) = liveData.observeAsState().value?.let { state: ArrowRepoState<ArrowRepoDetailFailure, Repo> ->
    when (state) {
        is ArrowRepoState.Loading -> LoadingView()
        is ArrowRepoState.Data -> RepoView(state.data)
        is ArrowRepoState.Error -> ErrorView(
            when (state.error) {
                is ArrowRepoDetailFailure.NoRepositoryFound -> stringResource(R.string.errors_repository_not_found)
                is ArrowRepoDetailFailure.RepositoryError -> state.error.throwable.message
                    ?: stringResource(R.string.errors_unknown)
            }.exhaustive
        )
    }.exhaustive
}
