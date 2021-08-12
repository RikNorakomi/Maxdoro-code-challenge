package com.rikvanvelzen.coding_challenge.ui.overview

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class OverviewLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<OverviewLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: OverviewLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): OverviewLoadStateViewHolder {
        return OverviewLoadStateViewHolder.create(parent, retry)
    }
}