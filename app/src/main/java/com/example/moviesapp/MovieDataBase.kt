package com.example.moviesapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movie::class, ScheduledNotification::class], version = 2)
abstract class MovieDataBase: RoomDatabase() {

    abstract val dao: MovieDao
    abstract val notificationDao : NotificationDao
}