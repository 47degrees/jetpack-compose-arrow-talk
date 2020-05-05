package com.fortyseven.degrees.kotlinmeetup.ui.composables

import androidx.compose.Composable
import androidx.compose.onCommit
import androidx.compose.remember
import androidx.compose.state
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

val <T> T.exhaustive
    get() = this

@Composable
val <T> LiveData<T>.observe: T?
    get() {
        val (resultState, resultCb) = state { value }
        val observer = remember { Observer<T> { resultCb(it) } }

        //The onCommit effect is a lifecycle effect that will execute [callback] every time the inputs to the effect have changed
        onCommit(this) {
            observeForever(observer)
            onDispose { removeObserver(observer) }
        }

        return resultState
    }
