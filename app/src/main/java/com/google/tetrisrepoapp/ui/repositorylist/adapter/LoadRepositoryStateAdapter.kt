package com.google.tetrisrepoapp.ui.repositorylist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.tetrisrepoapp.databinding.ItemRepositoryLoadingBinding

/** Adapter used for displaying loading progress and retry option at the bottom of the list of the currently displayed items */
class LoadRepositoryStateAdapter(
    private val retry: () -> Unit
) :
    LoadStateAdapter<LoadRepositoryStateAdapter.LoadRepoStateViewHolder>() {

    private var itemRepositoryLoadingBinding: ItemRepositoryLoadingBinding? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadRepoStateViewHolder {
        itemRepositoryLoadingBinding =
            ItemRepositoryLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadRepoStateViewHolder(itemRepositoryLoadingBinding!!, retry)
    }

    override fun onBindViewHolder(holder: LoadRepoStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    class LoadRepoStateViewHolder(
        private val parent: ItemRepositoryLoadingBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(parent.root) {

        fun bind(loadState: LoadState) {

            parent.btnRetry.setOnClickListener { retry() }

            if (loadState is LoadState.Error) {
                parent.btnRetry.visibility = View.VISIBLE
                parent.progressBar.visibility = View.GONE
            }

        }
    }

}
