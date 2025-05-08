package com.example.moviesapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.databinding.FragmentFavMovieBinding
import com.example.moviesapp.databinding.FragmentNotificationBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private var selectedImagePath: String? = null

    private val viewModel: NotificationViewModel by viewModels()
    private lateinit var notificationAdapter: NotificationAdapter

    //setting up the image uri
    private val imageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){uri->
        if(uri != null){
//            selectedImageUri = uri
//            requireContext().contentResolver.takePersistableUriPermission(
//                uri,
//                Intent.FLAG_GRANT_READ_URI_PERMISSION
//            )
//
//            binding.image.setImageURI(uri)

            val savedFile = saveImageToInternalStorage(uri)
            selectedImagePath = savedFile.absolutePath // Store for WorkManager
            binding.image.setImageBitmap(BitmapFactory.decodeFile(savedFile.absolutePath)) // Show image in ImageView
        }
    }

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted : Boolean->
            if(isGranted) {
                scheduleNotification()
            }
            else{
                val snackbar = Snackbar.make(binding.root,"Notification Permission Denied",Snackbar.LENGTH_SHORT)

                snackbar.setAction("Settings"){
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.fromParts("package", requireContext().packageName, null)
                    startActivity(intent)
                }
                snackbar.show()
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        askNotificationPermission()

        binding.scheduleNotibutton.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                scheduleNotification()
            }

        }

        binding.image.setOnClickListener {
            imageLauncher.launch("image/*")
        }

//Setting up the Notification Adapter to show scheduled Notifications
        notificationAdapter = NotificationAdapter(viewModel)
        binding.notificationRecyclerView.adapter = notificationAdapter
        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        viewModel.notification.observe(viewLifecycleOwner){list->
            notificationAdapter.submitList(list)

        }

//To avoid the focus from the EditText field if the user taps somewhere else on the screen and also hide the keyboard in that case
        binding.rootLayout.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                v.clearFocus()  // Clear focus from any currently focused view like EditText fields
                hideKeyboard()  // Hide keyboard when user taps somewhere else
                v.performClick() // Ensure proper click handling for accessibility
            }
            false
        }

    }

    private fun hideKeyboard() {
        val inputMethodManager = requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }


    private fun scheduleNotification(){
        val title = binding.title.text.toString()
        val message = binding.message.text.toString()

        if(title.isEmpty()){
            binding.title.error = "Title cannot be empty"
            return
        }

        if(message.isEmpty()){
            binding.message.error = "Title cannot be empty"
            return
        }

        //Getting the year,month,day information from the DatePicker and TimePicker
        val year = binding.date.year
        val month = binding.date.month
        val day = binding.date.dayOfMonth
        val hour = binding.time.hour
        val minute = binding.time.minute


        val calender = Calendar.getInstance()
        calender.set(year,month,day,hour,minute,0)
        calender.set(Calendar.MILLISECOND,0)

        val timeInMillis = calender.timeInMillis

        val time = timeInMillis


        val notification = ScheduledNotification(
            title = title,
            message = message,
            time = time,
            imagePath = selectedImagePath,   //selectedImageUri?.toString(),
            workId = ""
        )

        viewModel.scheduleNotification(notification)
        val timeMilli = time - System.currentTimeMillis()
        val timeSec = timeMilli/1000
        Toast.makeText(requireContext(),"Notification will go off in $timeSec sec",Toast.LENGTH_SHORT).show()
        clearInputFields()

    }

    //fun to clear the input fields after a notification is scheduled.
    private fun clearInputFields() {
       binding.title.setText("")
       binding.message.setText("")
       binding.image.setImageDrawable(null)

        val calendar = Calendar.getInstance()
        binding.date.updateDate(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        binding.time.hour = calendar.get(Calendar.HOUR_OF_DAY)
        binding.time.minute = calendar.get(Calendar.MINUTE)
    }


    private fun saveImageToInternalStorage(uri: Uri): File {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val fileName = "notif_img_${System.currentTimeMillis()}.jpg"
        val file = File(requireContext().filesDir, fileName)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        return file
    }



    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_GRANTED -> {
                }

//                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
//                    Toast.makeText(
//                        requireContext(),
//                        "Notification permission is needed to show notifications",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//                }

                else -> {
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            scheduleNotification()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}