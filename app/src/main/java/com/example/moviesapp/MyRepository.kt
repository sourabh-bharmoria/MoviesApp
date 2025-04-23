package com.example.moviesapp

import android.util.Log
import retrofit2.Response
import javax.inject.Inject

class MyRepository @Inject constructor(private val api: MovieApiService,private val ktorApiService: KtorApiService){

    suspend fun getMovieList(apiKey: String,page: Int) : Response<MovieResponse>{
        Log.d("Repository", apiKey)
        return api.getMovies(apiKey,page)
    }

    suspend fun getMoviesWithKtor(apiKey: String,page:Int) : MovieResponse {
        return ktorApiService.getMoviesktor(apiKey, page)
    }
}