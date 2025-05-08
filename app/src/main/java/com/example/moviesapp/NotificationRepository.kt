package com.example.moviesapp

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationRepository @Inject constructor(@ApplicationContext private val context: Context,val dao: NotificationDao) {

    //To get All scheduled Notification
    fun getAllScheduledNotification() : LiveData<List<ScheduledNotification>>{
        return dao.getAll()
    }

    //Fpr inserting Notification
    suspend fun insertNotification(notification: ScheduledNotification){
        return dao.insert(notification)
    }

    //For deleting Notification
    suspend fun deleteNotification(notification: ScheduledNotification) {
        WorkManager//also deleting from workManager so that it don't popup on notification bar
            .getInstance(context)
            .cancelWorkById(UUID.fromString(notification.workId))

        return dao.deleteByWorkId(notification.workId)//Deleting the Notifications based on their workId(in room)
    }

    //Error to be resolved pass the user entered values here
//    val notification = ScheduledNotification()
//    val data = notification.toWorkData()

    suspend fun scheduleNotification(notification: ScheduledNotification){
        val currentTime = System.currentTimeMillis()
        val delay = notification.time - currentTime

        Timber.d("Delay time: $delay")

        val data = notification.toWorkData()
        if(delay <= 0){
            Toast.makeText(context,"Notification cannot be scheduled for this Time",Toast.LENGTH_SHORT).show()
            return
        }

        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        //Scheduling the notification
        WorkManager
            .getInstance(context.applicationContext)
            .enqueue(workRequest)


        //Saving the notification with the Work Request Id
        val notificationWithId = notification.copy(workId = workRequest.id.toString())//copy() is used to create a new instance of data class with some modified values
//since kotlin data classes are immutable that's why we use the copy() method.
        insertNotification(notificationWithId)
    }
}