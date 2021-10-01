package com.google.tetrisrepoapp.di.datamodule

import com.google.tetrisrepoapp.data.remote.RemoteDataService
import com.google.tetrisrepoapp.data.remote.TetrisRepoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideRemoteDataService(apiService: TetrisRepoApiService): RemoteDataService {
        return RemoteDataService(apiService)
    }
}
