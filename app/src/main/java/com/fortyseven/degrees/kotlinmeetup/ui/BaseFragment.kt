package com.fortyseven.degrees.kotlinmeetup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fortyseven.degrees.kotlinmeetup.KotlinMeetupApp
import com.fortyseven.degrees.kotlinmeetup.R
import com.fortyseven.degrees.kotlinmeetup.di.AppComponent
import javax.inject.Inject

/**
 * Created by danieh on 02/05/2020
 */
abstract class BaseFragment : Fragment() {

    val appComponent: AppComponent by lazy {
        (activity?.application as KotlinMeetupApp).appComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment, container, false)
}

interface Actions