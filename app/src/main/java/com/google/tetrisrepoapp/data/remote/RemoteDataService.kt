package com.google.tetrisrepoapp.data.remote

import com.google.tetrisrepoapp.model.mapper.TetrisRepoRemoteMapper
import timber.log.Timber
import javax.inject.Inject

class RemoteDataService @Inject constructor(
    private val tetrisRepoApiService: TetrisRepoApiService,
    private val tetrisRepoRemoteMapper: TetrisRepoRemoteMapper
) {

    fun testMethod() {
        Timber.d("MARKO blabla")
    }
}
