package com.google.tetrisrepoapp.model.mapper

import com.google.gson.Gson
import com.google.tetrisrepoapp.model.error.ApiError
import okhttp3.ResponseBody

class RemoteErrorMapper(private val gson: Gson) {

    fun mapApiError(errorBody: ResponseBody?): ApiError {
        return gson.fromJson(errorBody?.string(), ApiError::class.java)
    }
}
