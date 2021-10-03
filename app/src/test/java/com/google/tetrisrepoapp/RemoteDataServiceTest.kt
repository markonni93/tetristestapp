package com.google.tetrisrepoapp

import com.google.gson.Gson
import com.google.tetrisrepoapp.model.RepoEntity
import com.google.tetrisrepoapp.model.error.ApiError
import com.google.tetrisrepoapp.model.error.ApiErrorBody
import com.google.tetrisrepoapp.model.mapper.RemoteErrorMapper
import com.google.tetrisrepoapp.model.mapper.RepoUiMapper
import com.google.tetrisrepoapp.model.mapper.TetrisRepoRemoteMapper
import com.google.tetrisrepoapp.model.response.PaginationStep
import com.google.tetrisrepoapp.model.response.PagingDataInfo
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
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

    private val mockPaginationInfo = listOf(
        PagingDataInfo(2, PaginationStep.NEXT),
        PagingDataInfo(40, PaginationStep.LAST)
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
        val response = mockRepositoryPagingSource.getRepositories("tetris", 1, 50)
        val mapped = repositoryMapper.mapRepoResponseToRepoEntity(response.body()!!)

        Assert.assertEquals(mockRepoEntities[0], mapped[0])
        Assert.assertEquals(mockRepoEntities[1], mapped[1])
        Assert.assertEquals(mockRepoEntities[2], mapped[2])
    }

    @Test
    fun testMappingResponseError() = runBlocking {
        val response = mockRepositoryPagingSource.getRepositoriesError()
        val mappedErrorResponse = remoteErrorMapper.mapApiError(response.errorBody())
        Assert.assertEquals(apiError, mappedErrorResponse)
    }

    @Test
    fun testMappingPagingInfo() = runBlocking {
        val response = mockRepositoryPagingSource.getRepositories("tetris", 1, 50)
        val mappedPagingInfo =
            repositoryMapper.mapPaginationInfo(response.headers()["link"].toString())

        Assert.assertEquals(mockPaginationInfo[0], mappedPagingInfo[0])
        Assert.assertEquals(mockPaginationInfo[1], mappedPagingInfo[1])
    }

    @Test
    fun testWrongPageMapping() = runBlocking {
        val response = mockRepositoryPagingSource.getRepositories("tetris", 9, 50)
        val mappedPagingInfo =
            repositoryMapper.mapPaginationInfo(response.headers()["link"].toString())

        Assert.assertNotEquals(mockPaginationInfo[0], mappedPagingInfo[0])
        Assert.assertEquals(mockPaginationInfo[1], mappedPagingInfo[1])
    }
}
