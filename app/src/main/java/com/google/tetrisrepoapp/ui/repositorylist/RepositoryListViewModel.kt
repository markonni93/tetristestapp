package com.google.tetrisrepoapp.ui.repositorylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.google.tetrisrepoapp.data.remote.paging.RemoteRepositoryPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RepositoryListViewModel @Inject constructor(private val remoteRepositoryPagingSource: RemoteRepositoryPagingSource) :
    ViewModel() {

    val flow = Pager(
        PagingConfig(
            initialLoadSize = INITIAL_LOAD_SIZE,
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE
        )
    ) {
        remoteRepositoryPagingSource
    }.flow
        .cachedIn(viewModelScope)

    companion object {
        private const val INITIAL_LOAD_SIZE = 50
        private const val PAGE_SIZE = 50
        private const val PREFETCH_DISTANCE = 25
    }
}
