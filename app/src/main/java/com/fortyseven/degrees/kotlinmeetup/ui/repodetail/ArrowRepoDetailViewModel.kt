package com.fortyseven.degrees.kotlinmeetup.ui.repodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.fx.IO
import arrow.fx.extensions.fx
import arrow.fx.extensions.io.async.effectMap
import arrow.integrations.kotlinx.unsafeRunIO
import com.fortyseven.degrees.kotlinmeetup.data.repository.ArrowReposRepository
import com.fortyseven.degrees.kotlinmeetup.ui.ArrowRepoState
import com.fortyseven.degrees.kotlinmeetup.ui.failure.ArrowRepoDetailFailure
import com.fortyseven.degrees.kotlinmeetup.ui.model.Repo
import com.fortyseven.degrees.kotlinmeetup.ui.model.toRepo
import kotlinx.coroutines.Dispatchers
import repos.RepoDb
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by danieh on 02/05/2020
 */
class ArrowRepoDetailViewModel @Inject constructor(
    private val arrowReposRepository: ArrowReposRepository
) : ViewModel() {

    private val _state =
        MutableLiveData<ArrowRepoState<ArrowRepoDetailFailure, Repo>>(ArrowRepoState.Loading())

    val state: LiveData<ArrowRepoState<ArrowRepoDetailFailure, Repo>>
        get() = _state

    fun init(repoName: String) {
        viewModelScope.unsafeRunIO(dispatchRepoDetail(repoName)) {}
    }

    private fun dispatchRepoDetail(repoName: String) = getArrowRepoDetail(repoName).attempt()
        .continueOn(Dispatchers.Main)
        .effectMap {
            it.fold(
                ifLeft = { throwable ->
                    Timber.e(throwable)
                    _state.postValue(
                        ArrowRepoState.Error(
                            ArrowRepoDetailFailure.RepositoryError(throwable)
                        )
                    )
                },
                ifRight = { _state.postValue(it) }
            )
        }

    private fun getArrowRepoDetail(repoName: String): IO<ArrowRepoState<ArrowRepoDetailFailure, Repo>> =
        IO.fx {
            continueOn(Dispatchers.IO)
            val repoDetail = arrowReposRepository.getArrowRepo(repoName).bind()
            repoDetail?.let { repoDb: RepoDb ->
                ArrowRepoState.Data<ArrowRepoDetailFailure, Repo>(repoDb.toRepo())
            } ?: ArrowRepoState.Error<ArrowRepoDetailFailure, Repo>(ArrowRepoDetailFailure.NoRepositoryFound)
        }
}
