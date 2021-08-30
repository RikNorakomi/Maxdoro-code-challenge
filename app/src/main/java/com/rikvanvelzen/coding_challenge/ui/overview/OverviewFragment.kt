package com.rikvanvelzen.coding_challenge.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.rikvanvelzen.coding_challenge.databinding.MainFragmentBinding
import com.rikvanvelzen.coding_challenge.ui.OverviewScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private val viewModel: OverviewScreenViewModel by viewModels()

    private val adapter = ResultsAdapter()
    private var dataFetchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = MainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(@NonNull view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initDataFetch()
    }


    private fun initDataFetch() {
        // Make sure we cancel the previous job before creating a new one
        dataFetchJob?.cancel()
        dataFetchJob = lifecycleScope.launch {

            // For the sake of this coding challenges I've hard-coded the query here
            // Which is usually ofc a no-no
            viewModel.getListData("Rembrandt van Rijn").collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initAdapter() {
        binding.list.adapter = adapter.withLoadStateFooter(
            footer = OverviewLoadStateAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->
            // show empty list
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            showEmptyList(isListEmpty)

            // Only show the list if refresh succeeds.
            binding.list.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    context,
                    "Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showEmptyList(show: Boolean) {
        binding.emptyList.visibility = if (show) VISIBLE else GONE
        binding.list.visibility = if (show) GONE else VISIBLE
    }
}