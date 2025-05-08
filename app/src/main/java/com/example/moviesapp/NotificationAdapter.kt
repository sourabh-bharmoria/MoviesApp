package com.example.moviesapp

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkManager
import com.example.moviesapp.databinding.ItemNotificationBinding
import timber.log.Timber
import java.util.Date
import java.util.Locale

class NotificationAdapter(private val viewModel: NotificationViewModel): ListAdapter<ScheduledNotification,NotificationAdapter.ViewHolder>(NotifDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder,position: Int) {
        val notification = getItem(position)
        holder.title.text = notification.title
//        holder.time.text = notification.time.toString()

        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
//SimpleDateFormat is a class used to format and parse dates in java/kotlin.First argument is the pattern, and
//Locale.getDefault() is used to use device's default locale.
        holder.time.text = timeFormat.format(Date(notification.time))//this takes the Date object and convert it into the pattern specified above.


        holder.deleteBtn.setOnClickListener {
            viewModel.deleteNotification(notification)

        }

    }


    class ViewHolder(binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.textViewTitle
        val time = binding.textViewTime
        val deleteBtn = binding.buttonDelete
    }
}

class NotifDiffCallBack: DiffUtil.ItemCallback<ScheduledNotification>(){
    override fun areItemsTheSame(oldItem: ScheduledNotification,newItem: ScheduledNotification): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ScheduledNotification,newItem: ScheduledNotification): Boolean {
        return oldItem.id == newItem.id
    }

}