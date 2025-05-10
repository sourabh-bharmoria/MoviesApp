package com.example.moviesapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree
@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        Timber.plant(object : DebugTree(){
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                super.log(priority, "global_tag$tag", message, t)
            }

            override fun createStackElementTag(element: StackTraceElement): String? {
                return String.format(
                    "%s:%s",
                    element.methodName,
                    element.lineNumber,
                    super.createStackElementTag(element)
                )

            }
        })

        Timber.d("App Created")
    }


    //Creating NotificationChannel for Notifications
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "Notification Channel Description"
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}