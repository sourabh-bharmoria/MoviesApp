package com.example.moviesapp

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): MovieDataBase{
       return Room.databaseBuilder(application, MovieDataBase::class.java,"Movies").build()

    }

    @Provides
    @Singleton
    fun provideMovieDao(db: MovieDataBase): MovieDao{
        return db.dao
    }

}