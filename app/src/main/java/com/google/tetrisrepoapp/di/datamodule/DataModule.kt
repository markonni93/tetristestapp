package com.google.tetrisrepoapp.di.datamodule

import com.google.gson.Gson
import com.google.tetrisrepoapp.data.remote.RemoteDataService
import com.google.tetrisrepoapp.data.remote.RepositoryApiService
import com.google.tetrisrepoapp.data.remote.paging.RemoteRepositoryPagingSource
import com.google.tetrisrepoapp.model.mapper.RemoteErrorMapper
import com.google.tetrisrepoapp.model.mapper.RepoUiMapper
import com.google.tetrisrepoapp.model.mapper.TetrisRepoRemoteMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * [DataModule] provides single instance of [RemoteDataService] and [RemoteRepositoryPagingSource] as well
 * as mapper classes that are required for fetching and mapping the data to proper objects.
 */
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideRemoteDataService(
        apiService: RepositoryApiService,
        tetrisRepoRemoteMapper: TetrisRepoRemoteMapper,
        remoteErrorMapper: RemoteErrorMapper
    ): RemoteDataService {
        return RemoteDataService(apiService, tetrisRepoRemoteMapper, remoteErrorMapper)
    }

    @Provides
    fun provideRemoteRepositoryPagingSource(
        remoteDataService: RemoteDataService,
        repoUiMapper: RepoUiMapper
    ): RemoteRepositoryPagingSource {
        return RemoteRepositoryPagingSource(remoteDataService, repoUiMapper)
    }

    @Provides
    fun provideTetrisRepoRemoteMapper() = TetrisRepoRemoteMapper()

    @Provides
    fun provideRepoUiMapper() = RepoUiMapper()

    @Provides
    fun provideRemoteErrorMapper(gson: Gson): RemoteErrorMapper {
        return RemoteErrorMapper(gson)
    }

    @Provides
    fun provideGson() = Gson()
}
