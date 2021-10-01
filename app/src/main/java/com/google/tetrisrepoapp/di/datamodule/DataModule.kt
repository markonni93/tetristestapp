package com.google.tetrisrepoapp.di.datamodule

import com.google.tetrisrepoapp.data.remote.RemoteDataService
import com.google.tetrisrepoapp.data.remote.TetrisRepoApiService
import com.google.tetrisrepoapp.model.mapper.TetrisRepoRemoteMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideRemoteDataService(
        apiService: TetrisRepoApiService,
        tetrisRepoRemoteMapper: TetrisRepoRemoteMapper
    ): RemoteDataService {
        return RemoteDataService(apiService, tetrisRepoRemoteMapper)
    }

    @Provides
    fun provideTetrisRepoRemoteMapper() = TetrisRepoRemoteMapper()
}
