package com.google.tetrisrepoapp

import com.google.tetrisrepoapp.data.remote.RepositoryApiService
import com.google.tetrisrepoapp.model.response.RemoteRepository
import com.google.tetrisrepoapp.model.response.RemoteRepositoryOwner
import com.google.tetrisrepoapp.model.response.RepositoryResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class MockRepositoryApiService : RepositoryApiService {

    override suspend fun getRepositories(
        query: String,
        page: Int,
        perPage: Int,
    ): Response<RepositoryResponse> {
        return Response.success(
            RepositoryResponse(
                25, false,
                listOf(
                    RemoteRepository(
                        1L,
                        "asgasg",
                        "tetris1",
                        "marko/tetris1",
                        RemoteRepositoryOwner("marko"),
                        12L,
                        false
                    ),
                    RemoteRepository(
                        2L,
                        "repasgasgo2",
                        "tetris2",
                        "peric/tetris2",
                        RemoteRepositoryOwner("peric"),
                        1112L,
                        true
                    ),
                    RemoteRepository(
                        3L,
                        "aasgsag",
                        "tetris2",
                        "zikic/tetris1",
                        RemoteRepositoryOwner("zikic"),
                        12451L,
                        true
                    )
                )
            )
        )
    }

    suspend fun getRepositoriesError(): Response<RepositoryResponse> {
        return Response.error(
            422,
            "{\"message\":\"Validation Failed\",\"errors\":[{\"resource\":\"Search\",\"field\":\"q\",\"code\":\"missing\"}],\"documentation_url\":\"https://docs.github.com/v3/search\"}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
    }
}
