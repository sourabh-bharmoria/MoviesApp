package com.example.moviesapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert
    suspend fun insert(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("Select * from Movie")//Livedata makes the fun suspend automatically
    fun getFavMovies(): LiveData<List<Movie>>

    @Query("SELECT EXISTS(SELECT 1 FROM Movie WHERE id = :id)")
    suspend fun isFavourite(id: Int): Boolean
}