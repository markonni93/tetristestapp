package com.google.tetrisrepoapp.ui.repositorylist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.google.tetrisrepoapp.model.RepoEntity
import com.google.tetrisrepoapp.model.ui.RepoUiItem

object RepoComparator : DiffUtil.ItemCallback<RepoUiItem>() {
    override fun areItemsTheSame(oldItem: RepoUiItem, newItem: RepoUiItem): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RepoUiItem, newItem: RepoUiItem): Boolean {
        return oldItem == newItem
    }
}
