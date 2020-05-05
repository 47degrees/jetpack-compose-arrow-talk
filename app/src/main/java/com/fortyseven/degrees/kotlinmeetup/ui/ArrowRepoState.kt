package com.fortyseven.degrees.kotlinmeetup.ui

/**
 * Created by danieh on 02/05/2020
 */
sealed class ArrowRepoState<Error, Success> {
    class Loading<Error, Success>() : ArrowRepoState<Error, Success>()

    class Data<Error, Success>(val data: Success) : ArrowRepoState<Error, Success>()

    class Error<Error, Success>(val error: Error) : ArrowRepoState<Error, Success>()
}
