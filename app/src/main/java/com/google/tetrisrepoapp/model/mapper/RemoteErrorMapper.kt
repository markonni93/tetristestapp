package com.google.tetrisrepoapp.model.mapper

import com.google.gson.Gson
import com.google.tetrisrepoapp.model.error.ApiError
import okhttp3.ResponseBody

/** Mapper used for constructing error returned by Github API */
class RemoteErrorMapper(private val gson: Gson) {

    /** @param errorBody is part of the response which we parse and construct [ApiError] */
    fun mapApiError(errorBody: ResponseBody?): ApiError {
        return gson.fromJson(errorBody?.string(), ApiError::class.java)
    }
}
