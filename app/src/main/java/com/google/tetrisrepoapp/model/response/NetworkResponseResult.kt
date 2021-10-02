package com.google.tetrisrepoapp.model.response

/**
 * Used as holder for all data and potential errors returned from the Github API.
 * @param data any object we create from parsing the fetched remote data.
 * @param message error message we construct when doing the error handling.
 */
sealed class NetworkResponseResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : NetworkResponseResult<T>(data)

    class Error<T>(message: String?) : NetworkResponseResult<T>(message = message)

}
