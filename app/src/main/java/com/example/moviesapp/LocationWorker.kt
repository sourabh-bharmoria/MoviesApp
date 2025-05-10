package com.example.moviesapp

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import timber.log.Timber
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class LocationWorker(appContext: Context,workerParams: WorkerParameters): Worker(appContext, workerParams) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(appContext)

    override fun doWork(): Result {
        //Checking permission first
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Timber.e("Location permission not granted")
            return Result.failure()
        }

        var result = Result.failure()

        val latch = CountDownLatch(1) // Because location is fetched asynchronously

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude

                Timber.d("Fresh location from Worker: $latitude $longitude")

                showLocationNotification(applicationContext, "$latitude $longitude")
                result = Result.success()
            } else {
                Timber.e("Location is null")
                result = Result.failure()
            }
            latch.countDown()
        }.addOnFailureListener {
            Timber.e("Failed to get location: ${it.message}")
            latch.countDown()
        }

        latch.await(10, TimeUnit.SECONDS) // Wait for the async location to return
        return result
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