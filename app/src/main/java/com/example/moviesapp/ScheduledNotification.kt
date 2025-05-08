package com.example.moviesapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_notifications")
data class ScheduledNotification(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val message: String,
    val time: Long,
    val imagePath: String?,
    val workId: String//Used to stored the scheduled Notification work Id
)
