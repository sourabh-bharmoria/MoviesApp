package com.example.moviesapp

import android.app.NotificationManager
import android.content.Context
import android.location.Location
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber

class LocationWorker(appContext: Context,workerParams: WorkerParameters): Worker(appContext, workerParams) {
    override fun doWork(): Result {

        val latitude = inputData.getDouble("latitude",0.0)
        val longitude = inputData.getDouble("longitude",0.0)

        Timber.d("Location: $latitude $longitude")

        val location = "$latitude $longitude"

        showLocationNotification(applicationContext,location)
        return Result.success()
    }

    private fun showLocationNotification(context: Context,location: String){

        val notification = NotificationCompat.Builder(context,"channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Location Notification")
            .setContentText(location)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1001,notification)


    }

}