package com.google.tetrisrepoapp.model.response

import com.google.gson.annotations.SerializedName

data class RemoteRepository(
    @SerializedName("id")
    val id: Long,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("owner")
    val owner: RemoteRepositoryOwner,
    @SerializedName("size")
    val size: Long,
    @SerializedName("has_wiki")
    val hasWiki: Boolean
)
