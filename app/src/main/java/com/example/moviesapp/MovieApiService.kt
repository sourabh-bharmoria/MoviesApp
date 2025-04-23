package com.example.moviesapp

import androidx.lifecycle.LiveData
import io.ktor.client.request.get
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieApiService {
    @GET("movie/popular")
   suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
   ) : Response<MovieResponse>

}