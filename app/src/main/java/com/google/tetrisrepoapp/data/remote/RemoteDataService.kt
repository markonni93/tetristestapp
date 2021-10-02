package com.google.tetrisrepoapp.data.remote

import com.google.tetrisrepoapp.model.mapper.TetrisRepoRemoteMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataService @Inject constructor(
    private val repositoryApiService: RepositoryApiService,
    private val tetrisRepoRemoteMapper: TetrisRepoRemoteMapper
) {

    suspend fun fetchRepositories(page: Int) =
        withContext(Dispatchers.IO) {
            try {
                val response =
                    repositoryApiService.getRepositories(TETRIS_QUERY, page = page, perPage = PER_PAGE)

                if (response.isSuccessful && response.body() != null) {
                    return@withContext tetrisRepoRemoteMapper.mapRepoResponseToResponseEntity(
                        response.body()!!
                    )
                } else {
                    throw Exception("Error happened because ${response.code()} and response body ${response.body()}")
                }
            } catch (e: Exception) {
                throw e
            }
        }

    companion object {
        private const val TETRIS_QUERY = "tetris"
        private const val PER_PAGE = 50
    }
}
