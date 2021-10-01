package com.google.tetrisrepoapp.model

/**
 * Model mapped from the response of fetched list of repositories with the we need.
 */
data class RepoEntity(
    val repositoryName: String,
    val ownerLoginName: String,
    val repositorySize: Long,
    val hasWiki: Boolean
)
