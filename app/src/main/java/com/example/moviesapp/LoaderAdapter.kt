package com.example.moviesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.LoaderMovieItemBinding

//Created to show loader when loading movies from other pages
class LoaderAdapter: LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,loadState: LoadState): LoaderViewHolder {
        val binding = LoaderMovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoaderViewHolder(binding)

    }

    override fun onBindViewHolder(holder: LoaderViewHolder,loadState: LoadState) {
        holder.progressBar.isVisible = loadState is LoadState.Loading
    }



    class LoaderViewHolder(binding: LoaderMovieItemBinding): RecyclerView.ViewHolder(binding.root){
         val progressBar = binding.progressBar
    }
}