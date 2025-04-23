//package com.example.moviesapp
//
//import com.example.moviesapp.BuildConfig // Import BuildConfig here
//
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//
//object keyObj {
//
//    @Provides
//    @Singleton
//    fun provideApiKey(): String {
//        return BuildConfig.TMDB_API_KEY
//    }
//}