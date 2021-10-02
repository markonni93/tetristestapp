package com.google.tetrisrepoapp.model.error

import com.google.gson.annotations.SerializedName

/** Data class for potential errors returned by Github API */
data class ApiError(
    val message: String,
    val errors: List<ApiErrorBody>?,
    @SerializedName("documentation_url")
    val documentationUrl: String
)

data class ApiErrorBody(val resource: String, val field: String, val code: String)
