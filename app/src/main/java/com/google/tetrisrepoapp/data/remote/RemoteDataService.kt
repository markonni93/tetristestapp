package com.google.tetrisrepoapp.data.remote

import com.google.tetrisrepoapp.data.remote.RemoteDataService.Companion.TETRIS_QUERY
import com.google.tetrisrepoapp.model.mapper.RemoteErrorMapper
import com.google.tetrisrepoapp.model.mapper.TetrisRepoRemoteMapper
import com.google.tetrisrepoapp.model.response.NetworkResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Use [RepositoryApiService] to fetch list of repositories from Github for the [TETRIS_QUERY] query
 */
class RemoteDataService @Inject constructor(
    private val repositoryApiService: RepositoryApiService,
    private val tetrisRepoRemoteMapper: TetrisRepoRemoteMapper,
    private val remoteErrorMapper: RemoteErrorMapper
) {

    suspend fun fetchRepositories(page: Int, perPage: Int) =
        withContext(Dispatchers.IO) {
            try {
                val response =
                    repositoryApiService.getRepositories(
                        query = TETRIS_QUERY,
                        page = page,
                        perPage = perPage
                    )

                if (response.isSuccessful && response.body() != null) {
                    val data =
                        tetrisRepoRemoteMapper.mapRepoResponseToRepoEntity(response.body()!!)
                    val paginationInfo =
                        tetrisRepoRemoteMapper.mapPaginationInfo(response.headers()[HEADER_PAGINATION_NAME].toString())
                    return@withContext NetworkResponseResult.Success(Pair(data, paginationInfo))
                } else {
                    val errorResponse = remoteErrorMapper.mapApiError(response.errorBody())
                    return@withContext NetworkResponseResult.Error("${response.code()} ${errorResponse.message}")
                }
            } catch (e: Exception) {
                return@withContext NetworkResponseResult.Error(e.message)
            }
        }

    companion object {
        private const val TETRIS_QUERY = "tetris"
        private const val HEADER_PAGINATION_NAME = "link"
    }
}
