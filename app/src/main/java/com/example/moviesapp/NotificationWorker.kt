package com.example.moviesapp

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber
import androidx.core.net.toUri

class NotificationWorker(appContext: Context,workerParams: WorkerParameters): Worker(appContext, workerParams) {
    override fun doWork(): Result {

        Timber.d("Worker is called")
        val title = inputData.getString("title") ?: "No title"
        val message = inputData.getString("message") ?: "No message"

  //For Location
        val latitude = inputData.getDouble("latitude",0.0)
        val longitude = inputData.getDouble("longitude",0.0)

        val location = "$latitude $longitude"
        showLocationNotification(applicationContext,location)
//        val imageUriString = inputData.getString("image")
//        Timber.d(imageUriString)
//        val imageUri = Uri.parse(imageUriString)

        val imagePath = inputData.getString("image")
        Timber.d("Path of image $imagePath")

        //Decode the image
     //   val bitmap = decodeImage(imageUri)
        val bitmap = BitmapFactory.decodeFile(imagePath) ?:  BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_launcher_background)



        Timber.d(title)
        Timber.d(message)

        showNotification(title,message,bitmap)

        return Result.success()

    }

//    private fun decodeImage(uri: Uri): Bitmap? {
//        return try {
//            // Open an InputStream from the URI
//            val inputStream = applicationContext.contentResolver.openInputStream(uri)
//            // Decoding the InputStream into a Bitmap
//            BitmapFactory.decodeStream(inputStream)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }

    private fun showNotification(title: String?, message: String?, bitmap: Bitmap?) {

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(applicationContext, "channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(System.currentTimeMillis().toInt(),notification)


    }

  //Fun to show Location Notification
    private fun showLocationNotification(context: Context,location: String){
        val notification = NotificationCompat.Builder(context,"channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Your Location")
            .setContentText(location)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1001,notification)
    }
}