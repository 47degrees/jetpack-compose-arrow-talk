package com.fortyseven.degrees.kotlinmeetup.di

import com.fortyseven.degrees.kotlinmeetup.KotlinMeetupApp
import com.fortyseven.degrees.kotlinmeetup.di.viewmodel.ViewModelModule
import com.fortyseven.degrees.kotlinmeetup.ui.repodetail.ArrowRepoDetailFragment
import com.fortyseven.degrees.kotlinmeetup.ui.repos.ArrowReposFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by danieh on 02/05/2020
 */
@Singleton
@Component(modules = [ViewModelModule::class, NetworkModule::class, DataModule::class])
interface AppComponent {

    fun inject(arrowReposFragment: ArrowReposFragment)

    fun inject(arrowRepoDetailFragment: ArrowRepoDetailFragment)

    companion object {
        fun create(context: KotlinMeetupApp, url: String, debug: Boolean): AppComponent =
            DaggerAppComponent.builder()
                .networkModule(NetworkModule(url, debug))
                .dataModule(DataModule(context))
                .build()
    }
}
