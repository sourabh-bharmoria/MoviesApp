package com.example.moviesapp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.ItemMovieBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MyAdapter(private val viewModel: MyViewModel) : PagingDataAdapter<Movie, MyAdapter.ViewHolder>(COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder,position: Int) {

        val movie = getItem(position)
        Timber.d("Binding movie: ${movie?.title}")

        movie?.let {
            holder.title.text = movie.title

            //     holder.image.src =movies[position].poster_path
            //Loading the image of the movie with the base URL
            val baseUrl = "https://image.tmdb.org/t/p/w500"
            val imageUrl = baseUrl + movie.poster_path
            Glide.with(holder.image.context)
                .load(imageUrl)
                .into(holder.image)

            //For setting the movies to favourite
            CoroutineScope(Dispatchers.IO).launch{
                var isFavourite = viewModel.isMovieFavourite(movie.id)
                Timber.d("favourite value $isFavourite")

                //UI update on main thread
                withContext(Dispatchers.Main){
                    holder.favourite.setImageResource(
                        if(isFavourite) R.drawable.baseline_favorite_24
                        else R.drawable.baseline_favorite_border_24
                    )

                    holder.favourite.setOnClickListener {
                      CoroutineScope(Dispatchers.IO).launch {
                          if(isFavourite){
                              viewModel.deleteMovie(movie)
                              withContext(Dispatchers.Main){
                                  holder.favourite.setImageResource(R.drawable.baseline_favorite_border_24)
                                  Toast.makeText(holder.itemView.context,"Removed from Favourites",Toast.LENGTH_SHORT).show()
                                  isFavourite = false
                              }

                          }else{
                              viewModel.insertMovie(movie)
                              withContext(Dispatchers.Main){
                                  holder.favourite.setImageResource(R.drawable.baseline_favorite_24)
                                  Toast.makeText(holder.itemView.context,"Added to Favourites",Toast.LENGTH_SHORT).show()
                                  isFavourite = true
                              }

                          }
                      }
                        // isFavourite = !isFavourite

                    }
                }

            }

        }


    }


    class ViewHolder(binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root){
        val title  = binding.title
        val image = binding.movieImage
        val favourite = binding.favouriteMovie
    }


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie,newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie,newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }
}