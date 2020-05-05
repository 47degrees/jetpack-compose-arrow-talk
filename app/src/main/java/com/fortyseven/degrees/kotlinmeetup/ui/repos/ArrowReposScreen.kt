package com.fortyseven.degrees.kotlinmeetup.ui.repos

import androidx.compose.Composable
import androidx.lifecycle.LiveData
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.graphics.ColorFilter
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.layout.preferredSize
import androidx.ui.layout.wrapContentSize
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.ripple
import androidx.ui.res.colorResource
import androidx.ui.res.imageResource
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.fortyseven.degrees.kotlinmeetup.R
import com.fortyseven.degrees.kotlinmeetup.ui.Actions
import com.fortyseven.degrees.kotlinmeetup.ui.ArrowRepoState
import com.fortyseven.degrees.kotlinmeetup.ui.composables.ArrowReposDivider
import com.fortyseven.degrees.kotlinmeetup.ui.composables.ErrorView
import com.fortyseven.degrees.kotlinmeetup.ui.composables.LoadingView
import com.fortyseven.degrees.kotlinmeetup.ui.composables.VStack
import com.fortyseven.degrees.kotlinmeetup.ui.composables.exhaustive
import com.fortyseven.degrees.kotlinmeetup.ui.composables.observe
import com.fortyseven.degrees.kotlinmeetup.ui.failure.ArrowReposFailure
import com.fortyseven.degrees.kotlinmeetup.ui.model.Repo

interface RepoActions : Actions {
    fun repoClick(repo: Repo)
}

@Composable
fun ArrowReposScreen(
    state: LiveData<ArrowRepoState<ArrowReposFailure, List<Repo>>>,
    actions: Actions
) = state.observe?.let { arrowReposState: ArrowRepoState<ArrowReposFailure, List<Repo>> ->
    when (arrowReposState) {
        is ArrowRepoState.Loading -> LoadingView()
        is ArrowRepoState.Data -> RepositoriesListView(arrowReposState.data, actions as RepoActions)
        is ArrowRepoState.Error -> {
            ErrorView(
                when (arrowReposState.error) {
                    is ArrowReposFailure.NoRepositories -> stringResource(R.string.errors_repositories_not_found)
                    is ArrowReposFailure.RepositoriesError -> arrowReposState.error.throwable.message
                        ?: stringResource(R.string.errors_unknown)
                }
            )
        }
    }.exhaustive
}

@Composable
fun RepositoriesListView(
    repositories: List<Repo>,
    actions: RepoActions
) = VStack {
    repositories.map {
        RepoViewClickable(it, actions)
        ArrowReposDivider()
    }
}

@Composable
fun RepoViewClickable(
    repo: Repo,
    actions: RepoActions
) = Clickable(
    modifier = Modifier.ripple(),
    onClick = { actions.repoClick(repo) }
) {
    RepoView(repo = repo)
}

@Composable
fun RepoView(repo: Repo) =
    Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.CenterStart)) {
            ArrowImage()
            Text(
                text = stringResource(id = R.string.repo_owner),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Text(text = repo.name, color = Color.Black, modifier = Modifier.padding(top = 8.dp))
        Text(
            text = repo.description,
            color = Color.Gray,
            maxLines = 3,
            modifier = Modifier.padding(top = 8.dp)
        )
        Row(modifier = Modifier.padding(top = 8.dp).fillMaxWidth()) {
            Text(
                text = repo.programming_language,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            StarImage()
            Text(text = "${repo.stargazers}")
        }
    }


@Composable
fun ArrowImage(modifier: Modifier = Modifier) =
    Image(imageResource(R.mipmap.arrow_logo), modifier.preferredSize(24.dp, 24.dp))

@Composable
fun StarImage(modifier: Modifier = Modifier) =
    Image(
        asset = imageResource(android.R.drawable.star_off),
        modifier = modifier.preferredSize(24.dp, 24.dp),
        colorFilter = ColorFilter.tint(colorResource(id = R.color.accentColor))
    )

@Preview
@Composable
fun RepoViewPreview() =
    MaterialTheme {
        RepoViewClickable(Repo.repoTest, object : RepoActions {
            override fun repoClick(repo: Repo) = Unit
        })
    }
