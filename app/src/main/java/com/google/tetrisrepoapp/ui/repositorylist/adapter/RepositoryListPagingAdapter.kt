package com.google.tetrisrepoapp.ui.repositorylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.tetrisrepoapp.databinding.ItemRepositoryHasWikiBinding
import com.google.tetrisrepoapp.databinding.ItemRepositoryRegularBinding
import com.google.tetrisrepoapp.model.ui.RepoUiItem

/** Adapter used for displaying paged data on the UI */
class RepositoryListPagingAdapter(diffCallback: DiffUtil.ItemCallback<RepoUiItem>) :
    PagingDataAdapter<RepoUiItem, RecyclerView.ViewHolder>(diffCallback) {

    private var itemRepositoryHasWikiBinding: ItemRepositoryHasWikiBinding? = null
    private var itemRepositoryRegularBinding: ItemRepositoryRegularBinding? = null

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is RepoUiItem.RepoUiRegularItem -> VIEWTYPE_REGULAR_REPO_ITEM
            is RepoUiItem.RepoUiHasWikiItem -> VIEWTYPE_HAS_WIKI_REPO_ITEM
            else -> throw IllegalStateException("Unknown item at position $position")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEWTYPE_REGULAR_REPO_ITEM -> {
                itemRepositoryRegularBinding = ItemRepositoryRegularBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                RegularRepoViewHolder(itemRepositoryRegularBinding!!)
            }
            VIEWTYPE_HAS_WIKI_REPO_ITEM -> {
                itemRepositoryHasWikiBinding = ItemRepositoryHasWikiBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HasWikiRepoViewHolder(itemRepositoryHasWikiBinding!!)
            }
            else -> {
                throw IllegalStateException("Cannot resolve view holder for viewType $viewType")
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is RepoUiItem.RepoUiHasWikiItem -> (holder as HasWikiRepoViewHolder).bind(item)
            is RepoUiItem.RepoUiRegularItem -> (holder as RegularRepoViewHolder).bind(item)
        }
    }

    class HasWikiRepoViewHolder(private val parent: ItemRepositoryHasWikiBinding) :
        RecyclerView.ViewHolder(parent.root) {

        fun bind(item: RepoUiItem.RepoUiHasWikiItem) {
            parent.tvRepoName.text = item.repositoryName
            parent.tvOwnerName.text = item.repositoryOwner
            parent.tvRepositorySize.text = item.repositorySize
        }
    }

    class RegularRepoViewHolder(private val parent: ItemRepositoryRegularBinding) :
        RecyclerView.ViewHolder(parent.root) {

        fun bind(item: RepoUiItem.RepoUiRegularItem) {
            parent.tvRepoName.text = item.repositoryName
            parent.tvOwnerName.text = item.repositoryOwner
            parent.tvRepositorySize.text = item.repositorySize
        }
    }

    companion object {
        private const val VIEWTYPE_REGULAR_REPO_ITEM = 0
        private const val VIEWTYPE_HAS_WIKI_REPO_ITEM = 1
    }

}
