package com.google.tetrisrepoapp

import com.google.gson.Gson
import com.google.tetrisrepoapp.model.RepoEntity
import com.google.tetrisrepoapp.model.error.ApiError
import com.google.tetrisrepoapp.model.error.ApiErrorBody
import com.google.tetrisrepoapp.model.mapper.RemoteErrorMapper
import com.google.tetrisrepoapp.model.mapper.RepoUiMapper
import com.google.tetrisrepoapp.model.mapper.TetrisRepoRemoteMapper
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RemoteDataServiceTest {

    private lateinit var repositoryMapper: TetrisRepoRemoteMapper
    private lateinit var remoteErrorMapper: RemoteErrorMapper
    private lateinit var repoUiMapper: RepoUiMapper
    private lateinit var mockRepositoryPagingSource: MockRepositoryApiService

    private val mockRepoEntities = listOf(
        RepoEntity(1L, "tetris1", "marko", 12L, false),
        RepoEntity(2L, "tetris2", "peric", 1112L, true),
        RepoEntity(3L, "tetris2", "zikic", 12451L, true)
    )

    private val apiError = ApiError(
        "Validation Failed",
        listOf(ApiErrorBody("Search", "q", "missing")),
        "https://docs.github.com/v3/search"
    )

    @Before
    fun setup() {
        repositoryMapper = TetrisRepoRemoteMapper()
        remoteErrorMapper = RemoteErrorMapper(Gson())
        repoUiMapper = RepoUiMapper()
        mockRepositoryPagingSource = MockRepositoryApiService()
    }

    @Test
    fun testMappingResponseSuccess() = runBlocking {
        val response = MockRepositoryApiService().getRepositories("tetris", 1, 50)
        val mapped = repositoryMapper.mapRepoResponseToRepoEntity(response.body()!!)

        Assert.assertEquals(mockRepoEntities[0], mapped[0])
        Assert.assertEquals(mockRepoEntities[1], mapped[1])
        Assert.assertEquals(mockRepoEntities[2], mapped[2])
    }

    @Test
    fun testMappingResponseError() = runBlocking {
        val response = MockRepositoryApiService().getRepositoriesError()
        val mappedErrorResponse = remoteErrorMapper.mapApiError(response.errorBody())
        Assert.assertEquals(apiError, mappedErrorResponse)
    }
}
