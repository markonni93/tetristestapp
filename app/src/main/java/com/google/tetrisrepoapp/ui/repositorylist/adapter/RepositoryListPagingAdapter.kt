package com.google.tetrisrepoapp.ui.repositorylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.tetrisrepoapp.databinding.ItemRepositoryHasWikiBinding
import com.google.tetrisrepoapp.model.RepoEntity

class RepositoryListPagingAdapter(diffCallback: DiffUtil.ItemCallback<RepoEntity>) :
    PagingDataAdapter<RepoEntity, RepositoryListPagingAdapter.RepoViewHolder>(diffCallback) {

    private var binding: ItemRepositoryHasWikiBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        binding = ItemRepositoryHasWikiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class RepoViewHolder(private val parent: ItemRepositoryHasWikiBinding) :
        RecyclerView.ViewHolder(parent.root) {

        fun bind(item: RepoEntity?) {
            parent.tvRepoLabel.text = item?.repositoryName
            parent.tvRepositorySize.text = item?.repositorySize.toString()
        }
    }
}
