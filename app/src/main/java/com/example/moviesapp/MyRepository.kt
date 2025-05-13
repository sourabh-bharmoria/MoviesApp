package com.example.moviesapp

import android.util.Log
import androidx.lifecycle.LiveData
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class MyRepository @Inject constructor(private val api: MovieApiService,private val ktorApiService: KtorApiService, val dao: MovieDao){

    suspend fun getMovieList(apiKey: String,page: Int,language: String) : Response<MovieResponse>{
        Timber.d("Repository $apiKey")
        return api.getMovies(apiKey,page,language)
    }

    suspend fun getMoviesWithKtor(apiKey: String,page:Int,language: String) : MovieResponse {
        return ktorApiService.getMoviesktor(apiKey, page, language)
    }

//Functions added for database and favourite movie functionality
    suspend fun getFavMovies(): LiveData<List<Movie>>{
        return dao.getFavMovies()
    }

    suspend fun insertFavMovie(movie: Movie){
        dao.insert(movie)
    }

    suspend fun deleteFavMovie(movie: Movie){
        dao.delete(movie)
    }

    suspend fun isMovieFavourite(id: Int): Boolean {
        return dao.isFavourite(id)
    }


}