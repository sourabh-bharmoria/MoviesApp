package com.example.moviesapp

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class KtorApiService @Inject constructor(private val client: HttpClient) {

    suspend fun getMoviesktor(apiKey: String,page: Int = 1): MovieResponse{
       return client.get("https://api.themoviedb.org/3/movie/popular"){
            parameter("api_key", apiKey)
            parameter("page",page)
        }.body()

    }
}