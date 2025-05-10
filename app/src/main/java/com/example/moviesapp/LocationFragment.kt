package com.example.moviesapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Snackbar
import androidx.core.content.ContextCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.moviesapp.databinding.FragmentLocationBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class LocationFragment : Fragment() {

    private  var _binding: FragmentLocationBinding? = null
    private  val binding get() = _binding!!

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted: Boolean->
            if(isGranted){
                Toast.makeText(requireContext(),"Location Permission Granted",Toast.LENGTH_SHORT).show()
            }else{
                binding.mySwitch.isChecked = false
                val snackBar = Snackbar.make(binding.rootLayout,"Location Permission Denied",
                    Snackbar.LENGTH_SHORT)

                snackBar.setAction("Settings"){
                    val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.fromParts("package", requireContext().packageName, null)
                    startActivity(intent)
                }
                snackBar.show()
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentLocationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        binding.mySwitch.setOnCheckedChangeListener {_,isChecked ->
            if(isChecked){ //Switch is on
                if(ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    fetchAndScheduleNotification()
                    Toast.makeText(requireContext(),"Location Permission On",Toast.LENGTH_SHORT).show()
                }else{

                    askLocationPermission()
                }


            }else{
                Toast.makeText(requireContext(),"Location Permission Stopped",Toast.LENGTH_SHORT).show()
                WorkManager.getInstance(requireContext()).cancelUniqueWork("LocationWork")
            }
        }
    }

    private fun askLocationPermission(){
        when{
            ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ->{
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)->{
                Toast.makeText(requireContext(),"Location Permission is needed to show location",Toast.LENGTH_SHORT).show()
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                binding.mySwitch.isChecked = false
            }

            else ->{
                locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun fetchAndScheduleNotification(){
        if(ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

        //Setting up the work request
        val workRequest = PeriodicWorkRequestBuilder<LocationWorker>(15, TimeUnit.MINUTES).build()

        WorkManager
          .getInstance(requireContext())
          .enqueueUniquePeriodicWork(
               "LocationWork",
               ExistingPeriodicWorkPolicy.UPDATE,
                workRequest
          )

        }else{
             Toast.makeText(requireContext(),"Error fetching Location",Toast.LENGTH_SHORT).show()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}