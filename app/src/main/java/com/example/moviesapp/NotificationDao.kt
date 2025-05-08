package com.example.moviesapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotificationDao {

    @Insert
    suspend fun insert(notification: ScheduledNotification)

//    @Delete
//    suspend fun delete(workId: String)//id used workId here

    @Query("DELETE FROM schedule_notifications WHERE workId = :workId")
    suspend fun deleteByWorkId(workId: String)


    @Query("Select * from schedule_notifications")
    fun getAll(): LiveData<List<ScheduledNotification>>

}