package com.example.moviesapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree
@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

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
}