package com.example.moviesapp

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class) //Tell Hilt where to install the module (what lifecycle the objects belong to)
@Module //class that tell Hilt how to create dependencies used for providing obj's like retrofit,okhttp
object RetrofitObj {

    @Provides //defines how to build an object
    @Singleton //only one instance of the object is created and is reused.
    fun provideRetrofit(): Retrofit {
       return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
//Generate a runtime implementation of our interface that handles the http operations.
    }

}