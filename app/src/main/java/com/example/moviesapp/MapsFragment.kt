package com.example.moviesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviesapp.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.heatmaps.HeatmapTileProvider
import timber.log.Timber

class MapsFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var clusterManager: ClusterManager<MyItem>

    private lateinit var mMap: GoogleMap

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


//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney").snippet("$sydney"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(delhi).title("Marker in delhi").snippet("$delhi"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(mumbai).title("Marker in Mumbai").snippet("$mumbai"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(kolkata).title("Marker in Kolkata").snippet("$kolkata"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(shimla).title("Marker in Shimla").snippet("$shimla"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(melbourne).title("Marker in Melbourne").snippet("$melbourne"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(newYork).title("Marker in New York").snippet("$newYork"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(sanFrancisco).title("Marker in San Francisco").snippet("$sanFrancisco"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(london).title("Marker in London").snippet("$london"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(paris).title("Marker in Paris").snippet("$paris"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(chandigarh).title("Marker in Chandigarh").snippet("$chandigarh"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(chennai).title("Marker in Chennai").snippet("$chennai"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(istanbul).title("Marker in Istanbul").snippet("$istanbul"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(berlin).title("Marker in Berlin").snippet("$berlin"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(moscow).title("Marker in Moscow").snippet("$moscow"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(cairo).title("Marker in Cairo").snippet("$cairo"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(capeTown).title("Marker in Cape Town").snippet("$capeTown"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(rio).title("Marker in Rio de janeiro").snippet("$rio"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(barcelona).title("Marker in Barcelona").snippet("$barcelona"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(tokyo).title("Marker in Tokyo").snippet("$tokyo"))?.showInfoWindow()
//        mMap.addMarker(MarkerOptions().position(toronto).title("Marker in Toronto").snippet("$toronto"))?.showInfoWindow()

//Changing the Marker color
//        val option = MarkerOptions().position(dubai).title("Marker in Dubai")
//        option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
//        mMap.addMarker(option)


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10f))

//Adding 1 HeatMap on the map
        addHeatMap()
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



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
