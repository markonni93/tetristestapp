package com.google.tetrisrepoapp.data.remote

import com.google.tetrisrepoapp.model.response.RepositoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryApiService {

    @GET("search/repositories")
    suspend fun getRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<RepositoryResponse>
}
