package com.google.tetrisrepoapp.model.response

sealed class NetworkResponseResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : NetworkResponseResult<T>(data)

    class Error<T>(message: String?) : NetworkResponseResult<T>(message = message)

}
