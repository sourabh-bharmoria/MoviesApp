package com.example.moviesapp

import android.health.connect.datatypes.units.Length
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.layout.Layout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.ItemMovieBinding

//Creating another recyclviewAdapter to show only the favourite movies from the popular movie list
class FavMovieAdapter(private val viewModel: MyViewModel):ListAdapter<Movie,FavMovieAdapter.FavMovieViewHolder>(MovieDiffCallback()) {

//    private val movies = mutableListOf<Movie>()


    private lateinit var binding: ItemMovieBinding

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): FavMovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavMovieViewHolder,position: Int) {
        val movie = getItem(position)
        holder.title.text = movie.title
        val baseUrl = "https://image.tmdb.org/t/p/w500"
        val imageUrl = baseUrl + movie.poster_path
        Glide.with(holder.image.context)
            .load(imageUrl)
            .into(holder.image)


        holder.favMovieIcon.setImageResource(R.drawable.baseline_favorite_24)
        holder.favMovieIcon.setOnClickListener {
            holder.favMovieIcon.setImageResource(R.drawable.baseline_favorite_border_24)
            viewModel.deleteMovie(movie)
            Toast.makeText(holder.itemView.context,"Removed from favourites", Toast.LENGTH_SHORT).show()
        }

    }

//    override fun getItemCount(): Int {
//       return movie.size
//    }

    class FavMovieViewHolder(binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.title
        val image = binding.movieImage
        val favMovieIcon = binding.favouriteMovie

    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}
