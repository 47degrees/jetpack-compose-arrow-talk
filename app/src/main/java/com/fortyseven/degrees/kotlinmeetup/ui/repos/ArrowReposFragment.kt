package com.fortyseven.degrees.kotlinmeetup.ui.repos

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.compose.Recomposer
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import com.fortyseven.degrees.kotlinmeetup.ui.BaseFragment
import com.fortyseven.degrees.kotlinmeetup.ui.lightThemeColors
import com.fortyseven.degrees.kotlinmeetup.ui.model.Repo

/**
 * Created by danieh on 02/05/2020
 */
class ArrowReposFragment : BaseFragment(), RepoActions {

    private val viewModel: ArrowReposViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        (view as ViewGroup).setContent(Recomposer.current()) {
            MaterialTheme(colors = lightThemeColors) {
                ArrowReposScreen(viewModel.state, this)
            }
        }
    }

    override fun repoClick(repo: Repo) =
        findNavController().navigate(
            ArrowReposFragmentDirections.actionReposFragmentToRepoDetailFragment(repo.name)
        )
}
