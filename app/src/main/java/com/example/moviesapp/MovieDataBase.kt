package com.example.moviesapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1)
abstract class MovieDataBase: RoomDatabase() {

    abstract val dao: MovieDao
}