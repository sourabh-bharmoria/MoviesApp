package com.example.moviesapp

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
//Dependency injection logic for the Location Permission
@Module
@InstallIn(SingletonComponent::class)
object LocationPermissionModule {

    @Singleton
    @Provides
    fun provideLocationPermission(context: Context): Boolean{
        return LocationPermissionUtil.hasLocationPermission(context)
    }

}