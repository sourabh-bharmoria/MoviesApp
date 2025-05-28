package com.example.moviesapp

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import timber.log.Timber

//Utility logic for the Location Permission
object LocationPermissionUtil {
    val REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun hasLocationPermission(context: Context): Boolean {
        return REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }
}