package com.example.moviesapp

import androidx.work.Data

    fun ScheduledNotification.toWorkData() : Data {
        return Data.Builder()
            .putString("title",title)
            .putString("message",message)
            .putLong("time",time)
            .putString("image",imagePath)
            .build()

    }
