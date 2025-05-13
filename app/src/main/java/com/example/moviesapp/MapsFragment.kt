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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import timber.log.Timber

class MapsFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

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

        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.addMarker(MarkerOptions().position(delhi).title("Marker in delhi"))
        mMap.addMarker(MarkerOptions().position(mumbai).title("Marker in Mumbai"))
        mMap.addMarker(MarkerOptions().position(kolkata).title("Marker in Kolkata"))
        mMap.addMarker(MarkerOptions().position(shimla).title("Marker in Shimla"))
        mMap.addMarker(MarkerOptions().position(melbourne).title("Marker in Melbourne"))
        mMap.addMarker(MarkerOptions().position(newYork).title("Marker in New York"))
        mMap.addMarker(MarkerOptions().position(sanFrancisco).title("Marker in San Francisco"))
        mMap.addMarker(MarkerOptions().position(london).title("Marker in London"))
        mMap.addMarker(MarkerOptions().position(paris).title("Marker in Paris"))
        mMap.addMarker(MarkerOptions().position(chandigarh).title("Marker in Chandigarh"))
        mMap.addMarker(MarkerOptions().position(chennai).title("Marker in Chennai"))
        mMap.addMarker(MarkerOptions().position(istanbul).title("Marker in Istanbul"))
        mMap.addMarker(MarkerOptions().position(berlin).title("Marker in Berlin"))
        mMap.addMarker(MarkerOptions().position(moscow).title("Marker in Moscow"))
        mMap.addMarker(MarkerOptions().position(cairo).title("Marker in Cairo"))
        mMap.addMarker(MarkerOptions().position(capeTown).title("Marker in Cape Town"))
        mMap.addMarker(MarkerOptions().position(rio).title("Marker in Rio de janeiro"))
        mMap.addMarker(MarkerOptions().position(barcelona).title("Marker in Barcelona"))
        mMap.addMarker(MarkerOptions().position(tokyo).title("Marker in Tokyo"))
        mMap.addMarker(MarkerOptions().position(toronto).title("Marker in Toronto"))









        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10f))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
