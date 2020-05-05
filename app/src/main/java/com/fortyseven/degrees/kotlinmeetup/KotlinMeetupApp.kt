package com.fortyseven.degrees.kotlinmeetup

import android.app.Application
import com.fortyseven.degrees.kotlinmeetup.di.AppComponent
import timber.log.Timber

/**
 * Created by danieh on 02/05/2020
 */
class KotlinMeetupApp : Application() {

    val appComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        AppComponent.create(this, BASE_URL, BuildConfig.DEBUG)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}
