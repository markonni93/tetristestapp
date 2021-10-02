package com.google.tetrisrepoapp.ui.repositorylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collectLatest { loadState ->

                if (loadState.refresh is LoadState.Error) {
                    constructAndShowToastMessage((loadState.refresh as LoadState.Error).error.message)
                } else if (loadState.append is LoadState.Error) {
                    constructAndShowToastMessage((loadState.append as LoadState.Error).error.message)
                }
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

    private fun constructAndShowToastMessage(errorMessage: String?) {
        Toast.makeText(requireContext(), errorMessage, LENGTH_LONG)
            .show()
    }
}
