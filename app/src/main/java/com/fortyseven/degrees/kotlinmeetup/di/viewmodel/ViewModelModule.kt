package com.fortyseven.degrees.kotlinmeetup.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fortyseven.degrees.kotlinmeetup.ui.repodetail.ArrowRepoDetailViewModel
import com.fortyseven.degrees.kotlinmeetup.ui.repos.ArrowReposViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by danieh on 02/05/2020
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ArrowReposViewModel::class)
    abstract fun bindsArrowReposViewModel(viewModel: ArrowReposViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArrowRepoDetailViewModel::class)
    abstract fun bindsArrowRepoDetailViewModel(viewModel: ArrowRepoDetailViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
