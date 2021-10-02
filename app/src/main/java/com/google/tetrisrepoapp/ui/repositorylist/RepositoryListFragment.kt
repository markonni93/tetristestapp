package com.google.tetrisrepoapp.ui.repositorylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.tetrisrepoapp.databinding.FragmentRepositoryListBinding
import com.google.tetrisrepoapp.ui.repositorylist.adapter.RepoComparator
import com.google.tetrisrepoapp.ui.repositorylist.adapter.RepositoryListPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RepositoryListFragment : Fragment() {

    private var _binding: FragmentRepositoryListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RepositoryListViewModel by viewModels()

    private lateinit var pagingAdapter: RepositoryListPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun setupRecyclerView() {
        pagingAdapter = RepositoryListPagingAdapter(RepoComparator)
        binding.rvRepositories.adapter = pagingAdapter
        binding.rvRepositories.addItemDecoration(
            DividerItemDecoration(
                binding.rvRepositories.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }
}
