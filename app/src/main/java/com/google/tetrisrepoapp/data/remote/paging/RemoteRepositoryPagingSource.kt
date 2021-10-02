package com.google.tetrisrepoapp.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.tetrisrepoapp.data.remote.RemoteDataService
import com.google.tetrisrepoapp.model.mapper.RepoUiMapper
import com.google.tetrisrepoapp.model.ui.RepoUiItem

class RemoteRepositoryPagingSource(
    private val remoteDataService: RemoteDataService,
    private val repoUiMapper: RepoUiMapper
) :
    PagingSource<Int, RepoUiItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoUiItem> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = remoteDataService.fetchRepositories(nextPageNumber)
            return LoadResult.Page(
                data = repoUiMapper.mapRepoUiItems(response),
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(Throwable("Something went wrong $e"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RepoUiItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
