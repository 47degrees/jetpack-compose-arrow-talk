package com.fortyseven.degrees.kotlinmeetup.ui.repodetail

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.compose.Recomposer
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import com.fortyseven.degrees.kotlinmeetup.ui.BaseFragment
import com.fortyseven.degrees.kotlinmeetup.ui.lightThemeColors

/**
 * Created by danieh on 02/05/2020
 */
class ArrowRepoDetailFragment : BaseFragment() {

    private val args by navArgs<ArrowRepoDetailFragmentArgs>()

    private val viewModel: ArrowRepoDetailViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(args.repoName)
        (view as ViewGroup).setContent(Recomposer.current()) {
            MaterialTheme(colors = lightThemeColors) {
                ArrowRepoDetailScreen(viewModel.state)
            }
        }
    }
}
