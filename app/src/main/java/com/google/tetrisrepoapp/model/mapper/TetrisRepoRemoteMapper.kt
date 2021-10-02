package com.google.tetrisrepoapp.model.mapper

import com.google.tetrisrepoapp.model.RepoEntity
import com.google.tetrisrepoapp.model.response.RepositoryResponse

/**
 * Maps the list of repositories fetched from the backend
 */
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
}
