package com.example.moviesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val repository: NotificationRepository):
    ViewModel() {

    val notification : LiveData<List<ScheduledNotification>> = repository.getAllScheduledNotification()

    fun insertNotification(notification: ScheduledNotification){
        viewModelScope.launch {
             repository.insertNotification(notification)
        }
    }

    fun deleteNotification(notification: ScheduledNotification){
        viewModelScope.launch {
             repository.deleteNotification(notification)
        }
    }

    fun scheduleNotification(notification: ScheduledNotification){
        viewModelScope.launch {
            repository.scheduleNotification(notification)
        }
    }
}