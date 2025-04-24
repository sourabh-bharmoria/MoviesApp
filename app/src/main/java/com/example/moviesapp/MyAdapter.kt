package com.example.moviesapp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.ItemMovieBinding

class MyAdapter: PagingDataAdapter<Movie, MyAdapter.ViewHolder>(COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder,position: Int) {

        val movie = getItem(position)
        Log.d("MyAdapter", "Binding movie: ${movie?.title}")

        movie?.let {
            holder.title.text = movie.title

            //For setting the movies to favourite
            var isFavourite = false
            holder.favourite.setOnClickListener {
                isFavourite = !isFavourite
               holder.favourite.setImageResource(if(isFavourite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
            }
            //     holder.image.src =movies[position].poster_path
            //Loading the image of the movie with the base URL
            val baseUrl = "https://image.tmdb.org/t/p/w500"
            val imageUrl = baseUrl + movie.poster_path
            Glide.with(holder.image.context)
                .load(imageUrl)
                .into(holder.image)
        }


    }


    class ViewHolder(binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root){
        val title  = binding.title
        val image = binding.movieImage
        val favourite = binding.favouriteMovie
    }


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}