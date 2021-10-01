package com.google.tetrisrepoapp.model.response

import com.google.gson.annotations.SerializedName

data class RemoteRepositoryOwner(
    @SerializedName("login")
    val login: String
)
