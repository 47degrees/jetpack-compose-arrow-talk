package com.fortyseven.degrees.kotlinmeetup.ui.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.fx.IO
import arrow.fx.extensions.fx
import arrow.fx.extensions.io.async.effectMap
import arrow.integrations.kotlinx.unsafeRunScoped
import com.fortyseven.degrees.kotlinmeetup.data.repository.ArrowReposRepository
import com.fortyseven.degrees.kotlinmeetup.ui.ArrowRepoState
import com.fortyseven.degrees.kotlinmeetup.ui.failure.ArrowReposFailure
import com.fortyseven.degrees.kotlinmeetup.ui.model.Repo
import com.fortyseven.degrees.kotlinmeetup.ui.model.toRepo
import kotlinx.coroutines.Dispatchers
import repos.RepoDb
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by danieh on 02/05/2020
 */
class ArrowReposViewModel @Inject constructor(
    private val arrowReposRepository: ArrowReposRepository
) : ViewModel() {

    private val _state =
        MutableLiveData<ArrowRepoState<ArrowReposFailure, List<Repo>>>(ArrowRepoState.Loading())

    val state: LiveData<ArrowRepoState<ArrowReposFailure, List<Repo>>>
        get() = _state

    fun init() {
        getArrowRepos().unsafeRunScoped(viewModelScope) {}
    }

    private fun getArrowRepos() = getArrowReposIO().attempt()
        .continueOn(Dispatchers.Main)
        .effectMap {
            it.fold(
                ifLeft = { throwable ->
                    Timber.e(throwable)
                    _state.postValue(
                        ArrowRepoState.Error(
                            ArrowReposFailure.RepositoriesError(throwable)
                        )
                    )
                },
                ifRight = { _state.postValue(it) }
            )
        }

    private fun getArrowReposIO(): IO<ArrowRepoState<ArrowReposFailure, List<Repo>>> =
        IO.fx {
            continueOn(Dispatchers.IO)
            val repositories: List<RepoDb> = arrowReposRepository.getArrowRepos().bind()
            if (repositories.isNotEmpty()) {
                ArrowRepoState.Data<ArrowReposFailure, List<Repo>>(repositories.map { it.toRepo() })
            } else {
                ArrowRepoState.Error<ArrowReposFailure, List<Repo>>(ArrowReposFailure.NoRepositories)
            }
        }
}
