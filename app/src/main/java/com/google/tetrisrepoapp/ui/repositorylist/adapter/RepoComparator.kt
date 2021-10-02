package com.google.tetrisrepoapp.ui.repositorylist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.google.tetrisrepoapp.model.RepoEntity

object RepoComparator : DiffUtil.ItemCallback<RepoEntity>() {
    override fun areItemsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean {
        // Id is unique.
        return oldItem.repositoryName == newItem.repositoryName
    }

    override fun areContentsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean {
        return oldItem == newItem
    }
}
