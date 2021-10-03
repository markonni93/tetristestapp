package com.google.tetrisrepoapp.model.mapper

import android.net.Uri
import com.google.tetrisrepoapp.model.RepoEntity
import com.google.tetrisrepoapp.model.response.PaginationStep
import com.google.tetrisrepoapp.model.response.PagingDataInfo
import com.google.tetrisrepoapp.model.response.RepositoryResponse

/** Mapper used for constructing the list of repositories fetched from Github and also for parsing
 * information about pages used for pagination
 * */
class TetrisRepoRemoteMapper {

    fun mapRepoResponseToRepoEntity(response: RepositoryResponse): List<RepoEntity> {
        return response.items.map { remoteRepositoryResponse ->
            RepoEntity(
                id = remoteRepositoryResponse.id,
                repositoryName = remoteRepositoryResponse.name,
                ownerLoginName = remoteRepositoryResponse.owner.login,
                repositorySize = remoteRepositoryResponse.size,
                hasWiki = remoteRepositoryResponse.hasWiki
            )
        }
    }

    /**
     * @param paginationHeader is extracted from the response and it contains necessary information
     * about pagination page and the step it is connected to
     * @sample <https://api.github.com/search/repositories?q=tetris&page=19&per_page=50>; rel="prev"
     */
    fun mapPaginationInfo(paginationHeader: String): List<PagingDataInfo> {
        return paginationHeader.split(COMMA_DELIMITER)
            .map { it.split(SEMICOLON_DELIMITER) }
            .map {
                PagingDataInfo(
                    getPage(it.first()), getPaginationStep(
                        it.last().substring(6, it.last().length - 1)
                    )
                )
            }
    }

    /**
     * Used for parsing the url in order to extract page query from it
     */
    private fun getPage(link: String): Int? {
        return try {
            val uri = Uri.parse(link)
            uri.getQueryParameter(PAGE_QUERY_PARAM)?.toInt()
        } catch (e: Exception) {
            null
        }
    }

    /** Create enum value from the pagination step string */
    private fun getPaginationStep(step: String): PaginationStep {
        return PaginationStep.valueOf(step.uppercase())
    }

    companion object {
        private const val PAGE_QUERY_PARAM = "page"
        private const val COMMA_DELIMITER = ","
        private const val SEMICOLON_DELIMITER = ";"
    }
}


