package com.example.moviesapp

import android.Manifest
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.moviesapp.databinding.FragmentMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.example.moviesapp.LocationPermissionUtil
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import androidx.core.graphics.toColorInt
import com.google.android.gms.maps.model.CameraPosition
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var clusterManager: ClusterManager<MyItem>

//    private var userMarker: Marker? = null
    private var locationCircle: Circle? = null

    private lateinit var locationCallback: LocationCallback
    private var fusedLocationClient: FusedLocationProviderClient? = null

    private lateinit var mMap: GoogleMap


    // Register permission launcher using ActivityResult API
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all { it.value }
        if (granted) {
            startLocationUpdates()
//            enableMyLocationOnMap()
        } else {
            val rootView = requireActivity().findViewById<View>(android.R.id.content)

            val snackBar = Snackbar.make(rootView,"Location Permission Denied", Snackbar.LENGTH_INDEFINITE)

            snackBar.setAction("settings"){
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.fromParts("package", requireContext().packageName, null)
                startActivity(intent)

            }
            snackBar.show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map_fragment_container) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Initializing FusedLocation Client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation ?: return

                val latLng = LatLng(location.latitude, location.longitude)


//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17f))

                val updateInterval = 4000L

                updateCircle(latLng, updateInterval)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        clusterManager = ClusterManager(requireContext(), mMap)//Creating clusterManager instance

        mMap.setOnCameraIdleListener(clusterManager)
        mMap.setOnMarkerClickListener(clusterManager)


        val sydney = LatLng(-34.0, 151.0)
        val delhi = LatLng(28.6139, 77.2090)
        val mumbai = LatLng(19.0760, 72.8777)
        val kolkata = LatLng(22.5726, 88.3639)
        val shimla = LatLng(31.1048, 77.1734)
        val melbourne = LatLng(-37.8136, 144.9631)
        val newYork = LatLng(40.7128, -74.0060)
        val sanFrancisco = LatLng(37.7749, -122.4194)
        val london = LatLng(51.5074, -0.1278)
        val paris = LatLng(48.8566, 2.3522)
        val chandigarh = LatLng(30.7333, 76.7794)
        val chennai = LatLng(13.0827, 80.2707)
        val istanbul = LatLng(41.0082, 28.9784)
        val berlin = LatLng(52.5200, 13.4050)
        val moscow = LatLng(55.7558, 37.6173)
        val cairo = LatLng(30.0444, 31.2357)
        val capeTown = LatLng(33.9221, 18.4231)
        val rio = LatLng(22.9068, 43.1729)
        val barcelona = LatLng(41.3874, 2.1686)
        val tokyo = LatLng(35.6764, 139.6500)
        val toronto = LatLng(43.6532, 79.3832)
        val dubai = LatLng(25.2048, 55.2708)

        clusterManager.addItem(MyItem(-34.0, 151.0,"Marker in Sydney","$sydney"))
        clusterManager.addItem(MyItem(28.6139, 77.2090,"Marker in delhi","$delhi"))

//Added locations near delhi to check the clustering
        clusterManager.addItem(MyItem(28.6315, 77.2167, "Connaught Place", "Popular commercial hub"))
        clusterManager.addItem(MyItem(28.6129, 77.2295, "India Gate", "War memorial"))
        clusterManager.addItem(MyItem(28.5244, 77.1855, "Qutub Minar", "Historical monument"))
        clusterManager.addItem(MyItem(28.5494, 77.2001, "Hauz Khas", "Trendy urban village"))
        clusterManager.addItem(MyItem(28.6518, 77.1905, "Karol Bagh", "Shopping destination"))
        clusterManager.addItem(MyItem(28.5672, 77.2430, "Lajpat Nagar", "Market and residential area"))
        clusterManager.addItem(MyItem(28.5759, 77.2260, "South Ex", "Upscale shopping area"))
        clusterManager.addItem(MyItem(28.6420, 77.1235, "Rajouri Garden", "Residential and market area"))
        clusterManager.addItem(MyItem(28.7345, 77.1038, "Rohini Sector 7", "Northwest Delhi"))
        clusterManager.addItem(MyItem(28.7013, 77.1320, "Pitampura", "Residential locality"))



        clusterManager.addItem(MyItem(19.0760, 72.8777,"Marker in Mumbai","$mumbai"))
        clusterManager.addItem(MyItem(22.5726, 88.3639,"Marker in Kolkata","$kolkata"))
        clusterManager.addItem(MyItem(31.1048, 77.1734,"Marker in Shimla","$shimla"))
        clusterManager.addItem(MyItem(-37.8136, 144.9631,"Marker in Melbourne","$melbourne"))
        clusterManager.addItem(MyItem(40.7128, -74.0060,"Marker in New York","$newYork"))
        clusterManager.addItem(MyItem(37.7749, -122.4194,"Marker in San Francisco","$sanFrancisco"))
        clusterManager.addItem(MyItem(51.5074, -0.1278,"Marker in london","$london"))
        clusterManager.addItem(MyItem(48.8566, 2.3522,"Marker in Paris","$paris"))
        clusterManager.addItem(MyItem(30.7333, 76.7794,"Marker in Chandigarh","$chandigarh"))
        clusterManager.addItem(MyItem(13.0827, 80.2707,"Marker in Chennai","$chennai"))
        clusterManager.addItem(MyItem(41.0082, 28.9784,"Marker in Istanbul","$istanbul"))
        clusterManager.addItem(MyItem(52.5200, 13.4050,"Marker in Berlin","$berlin"))
        clusterManager.addItem(MyItem(55.7558, 37.6173,"Marker in Moscow","$moscow"))
        clusterManager.addItem(MyItem(30.0444, 31.2357,"Marker in Cairo","$cairo"))
        clusterManager.addItem(MyItem(33.9221, 18.4231,"Marker in Cape Town","$capeTown"))
        clusterManager.addItem(MyItem(22.9068, 43.1729,"Marker in Rio de janeiro","$rio"))
        clusterManager.addItem(MyItem(41.3874, 2.1686,"Marker in Barcelona","$barcelona"))
        clusterManager.addItem(MyItem(35.6764, 139.6500,"Marker in Tokyo","$tokyo"))
        clusterManager.addItem(MyItem(43.6532, 79.3832,"Marker in Toronto","$toronto"))


        clusterManager.cluster()

//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10f))

//Adding 1 HeatMap on the map
        addHeatMap()

//this is added for checking location permission
        if (LocationPermissionUtil.hasLocationPermission(requireContext())) {
            startLocationUpdates()
//            enableMyLocationOnMap()
        } else {
           requestPermissionLauncher.launch(LocationPermissionUtil.REQUIRED_PERMISSIONS)
        }
    }

    private fun addHeatMap(){
        val points = mutableListOf<LatLng>()
        //Beijing lat and lang are used
        val baseLat = 39.9042
        val baseLng = 116.4074

        // Creating many points near Beijing to see the HeatMap
        for (i in 0 until 100) {
            val lat = baseLat + Math.random() * 0.01
            val lng = baseLng + Math.random() * 0.01
            points.add(LatLng(lat, lng))
        }

        val heatMapProvider = HeatmapTileProvider.Builder()
            .data(points)//This takes values in list format and can take multiple values
            .radius(20)
            .build()

        mMap.addTileOverlay(TileOverlayOptions().tileProvider(heatMapProvider))
    }

    private fun updateCircle(newLatLng: LatLng, duration: Long){
        //If the location circle doesn't exist it creates the circle
        if(locationCircle == null){
            locationCircle = mMap.addCircle(
                CircleOptions()
                    .center(newLatLng)
                    .radius(10.0)
                    .strokeColor(Color.WHITE)
                    .fillColor("#4285F4".toColorInt())
                    .zIndex(10f)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng,17f))
        }else {
            //if the location circle exists it animates the circle to new position
            animateCircleMovement(locationCircle!!, newLatLng, duration)
            animateCameraMovement(newLatLng, duration)

        }
    }

    private fun animateCircleMovement(circle: Circle, finalPosition: LatLng, duration: Long) {
        val startLatLng = circle.center
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = duration // animation duration should be equal to the time needed for location update
        valueAnimator.interpolator = LinearInterpolator() //LinearInterpolator is used to ensure the animation progress is at a constant speed

        valueAnimator.addUpdateListener { animation ->
            val fraction = animation.animatedFraction //fraction refers to the progress of animation between 0.0 to 1.0
            val lat = (finalPosition.latitude - startLatLng.latitude) * fraction + startLatLng.latitude
            val lng = (finalPosition.longitude - startLatLng.longitude) * fraction + startLatLng.longitude
            circle.center = LatLng(lat, lng)
        }

        valueAnimator.start() //start the animation loop
    }

    private fun animateCameraMovement(targetLatLng: LatLng, duration: Long) {
        val currentCameraPosition = mMap.cameraPosition

        val cameraPosition = CameraPosition.Builder(currentCameraPosition)
            .target(targetLatLng)  // Center to new location
            .zoom(currentCameraPosition.zoom) // Keep same zoom
            .bearing(currentCameraPosition.bearing) // Keep same rotation
            .tilt(currentCameraPosition.tilt) // Keep same tilt
            .build()

        mMap.animateCamera(     //Animate from the current position to new camera position.
            CameraUpdateFactory.newCameraPosition(cameraPosition),//Uses cameraUpdateFactory to update
            duration.toInt(),  // animation duration of camera same as for the circle icon movement
            null
        )
    }



    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 4000L
        ).build()

        // Permission already checked before calling this function
        fusedLocationClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

//    @SuppressLint("MissingPermission")
//    private fun enableMyLocationOnMap() {
//        if (::mMap.isInitialized) {
//            mMap.isMyLocationEnabled = true
//        }
//    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient?.removeLocationUpdates(locationCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
