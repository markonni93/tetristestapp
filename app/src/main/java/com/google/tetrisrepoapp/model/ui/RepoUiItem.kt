package com.google.tetrisrepoapp.model.ui

sealed class RepoUiItem {

    data class RepoUiRegularItem(
        val id: Long,
        val repositoryName: String,
        val repositoryOwner: String,
        val repositorySize: String
    ) : RepoUiItem()

    data class RepoUiHasWikiItem(
        val id: Long,
        val repositoryName: String,
        val repositoryOwner: String,
        val repositorySize: String
    ) : RepoUiItem()
}
