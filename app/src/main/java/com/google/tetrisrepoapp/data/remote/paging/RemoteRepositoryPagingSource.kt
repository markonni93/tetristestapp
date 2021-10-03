package com.google.tetrisrepoapp.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.tetrisrepoapp.data.remote.RemoteDataService
import com.google.tetrisrepoapp.model.mapper.RepoUiMapper
import com.google.tetrisrepoapp.model.response.PaginationStep
import com.google.tetrisrepoapp.model.ui.RepoUiItem

/**
 * Handles pagination requests for fetching list of repositories
 */
class RemoteRepositoryPagingSource(
    private val remoteDataService: RemoteDataService,
    private val repoUiMapper: RepoUiMapper
) :
    PagingSource<Int, RepoUiItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoUiItem> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = remoteDataService.fetchRepositories(nextPageNumber, params.loadSize)

            return if (response.data != null) {
                LoadResult.Page(
                    data = repoUiMapper.mapRepoUiItems(response.data.first),
                    prevKey = response.data.second.firstOrNull { it.step == PaginationStep.PREV }?.page,
                    nextKey = response.data.second.firstOrNull { it.step == PaginationStep.NEXT }?.page
                )
            } else {
                LoadResult.Error(Exception(response.message))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RepoUiItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
