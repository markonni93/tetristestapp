package com.google.tetrisrepoapp.model.mapper

import com.google.tetrisrepoapp.model.RepoEntity
import com.google.tetrisrepoapp.model.ui.RepoUiItem

/** Mapper used for constructing list of items that we display on UI */
class RepoUiMapper {

    fun mapRepoUiItems(repoEntities: List<RepoEntity>): List<RepoUiItem> {
        return repoEntities.map { repository ->
            if (repository.hasWiki) {
                constructRepoUiHasWikiItem(repository)
            } else {
                constructRepoUiRegularItem(repository)
            }
        }
    }

    private fun constructRepoUiRegularItem(repository: RepoEntity) = RepoUiItem.RepoUiRegularItem(
        id = repository.id,
        repositoryName = repository.repositoryName,
        repositoryOwner = repository.ownerLoginName,
        repositorySize = repository.repositorySize.toString()
    )

    private fun constructRepoUiHasWikiItem(repository: RepoEntity) = RepoUiItem.RepoUiHasWikiItem(
        id = repository.id,
        repositoryName = repository.repositoryName,
        repositoryOwner = repository.ownerLoginName,
        repositorySize = repository.repositorySize.toString()
    )
}
