package com.example.moviesapp

import android.health.connect.datatypes.units.Length
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.layout.Layout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.ItemMovieBinding

//Creating another recyclviewAdapter to show only the favourite movies from the popular movie list
class FavMovieAdapter(val movies: List<Movie>,private val viewModel: MyViewModel):RecyclerView.Adapter<FavMovieAdapter.FavMovieViewHolder>() {

    private lateinit var binding: ItemMovieBinding

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): FavMovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavMovieViewHolder,position: Int) {
        holder.title.text = movies[position].title
        val baseUrl = "https://image.tmdb.org/t/p/w500"
        val imageUrl = baseUrl + movies[position].poster_path
        Glide.with(holder.image.context)
            .load(imageUrl)
            .into(holder.image)


        holder.favMovieIcon.setImageResource(R.drawable.baseline_favorite_24)
        holder.favMovieIcon.setOnClickListener {
            holder.favMovieIcon.setImageResource(R.drawable.baseline_favorite_border_24)
            viewModel.deleteMovie(movies[position])
            Toast.makeText(holder.itemView.context,"Removed from favourites", Toast.LENGTH_SHORT).show()
        }



    }

    override fun getItemCount(): Int {
       return movies.size
    }

    class FavMovieViewHolder(binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.title
        val image = binding.movieImage
        val favMovieIcon = binding.favouriteMovie

    }
}