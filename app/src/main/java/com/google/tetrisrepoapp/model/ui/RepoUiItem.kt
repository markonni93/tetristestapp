package com.google.tetrisrepoapp.model.ui

sealed class RepoUiItem {

    abstract val id: Long

    data class RepoUiRegularItem(
        override val id: Long,
        val repositoryName: String,
        val repositoryOwner: String,
        val repositorySize: String
    ) : RepoUiItem()

    data class RepoUiHasWikiItem(
        override val id: Long,
        val repositoryName: String,
        val repositoryOwner: String,
        val repositorySize: String
    ) : RepoUiItem()
}
