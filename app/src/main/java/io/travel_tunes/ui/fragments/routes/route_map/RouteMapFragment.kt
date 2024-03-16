package io.travel_tunes.ui.fragments.routes.route_map

import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import io.travel_tunes.R
import io.travel_tunes.databinding.FragmentRouteMapBinding
import io.travel_tunes.model.route.RouteItemInfo
import io.travel_tunes.utils.extencions.changeVisibility
import io.travel_tunes.utils.extencions.parcelable

internal class RouteMapFragment : Fragment(),
    OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentRouteMapBinding
    private lateinit var viewModelFactory: RouteMapViewModelFactory
    private lateinit var viewModel: RouteMapViewModel

    private val places = ArrayList<Any>()


    private val callback = OnMapReadyCallback { googleMap ->
//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker at interesting point"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        val polyline1 = googleMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .add(
                    LatLng(56.995246, 40.982064),
                    LatLng(56.995400, 40.979892),
                    LatLng(56.995663, 40.978882),
                    LatLng(56.999137, 40.973710),
                    LatLng(57.001521, 40.973780),
                    LatLng(57.010588, 40.972023)
                )
                .color(Color.BLUE)
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(polyline1.points[4], 14f))

    }



    companion object {
        private const val EXTRA_ROUTE_INFO = "route_info"
        fun getInstance(routeItemInfo: RouteItemInfo): RouteMapFragment {
            val args = Bundle()
            val fragment = RouteMapFragment()
            args.putParcelable(EXTRA_ROUTE_INFO, routeItemInfo)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteMapBinding.inflate(inflater, container, false)
        return binding.root
//        return inflater.inflate(R.layout.fragment_route_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        initViews()
        initViewModel()

        places.add(LatLng(56.995246, 40.982064))
        places.add(LatLng(56.999137, 40.973710))
        places.add(LatLng(57.001521, 40.973780))
        places.add(LatLng(57.010588, 40.972023))

//        binding = FragmentRouteMapBinding.inflate(layoutInflater)
////        setContentView(binding.root)


    }

    private fun initViews() {
        initToolbar()
    }

    private fun initToolbar() {
        with(binding.toolbarLayout) {
            toolbarBackArrow.apply {
                changeVisibility(true)
                setOnClickListener {
                    activity?.onBackPressed()
                }
            }
            toolbarSettings.apply {
                changeVisibility(true)
                setOnClickListener {
                    android.widget.Toast.makeText(
                        requireContext(),
                        "Неплохо бы сначала добавить экран, а потом уже тыкать 😉",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun initViewModel() {
        val routeItemInfo = arguments?.parcelable<RouteItemInfo>(EXTRA_ROUTE_INFO) ?: return
        viewModelFactory = RouteMapViewModelFactory(routeItemInfo)

        viewModel = ViewModelProvider(this, viewModelFactory)[RouteMapViewModel::class.java]

        viewModel.routeInfo.observe(viewLifecycleOwner) { routeInfo ->
            // FIXME: наименование для toolbar задать и точки на карте отрисовать
//            showRoutePoints(routeInfo)
            // тут может быть реализация отображения маркеров на карте (points)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerClickListener(this)
        mMap.uiSettings.isZoomControlsEnabled = true


//        mMap.isMyLocationEnabled = true

        val markers = arrayOfNulls<MarkerOptions>(places.size)
        for (i in places.indices) {
            markers[i] = MarkerOptions()
                .position(places[i] as LatLng)
            googleMap.addMarker(markers[i]!!)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(places[1] as LatLng, 16f))
        }


        val polylineOptions = PolylineOptions()
            .add(LatLng(56.995246, 40.982064))
            .add(LatLng(56.995400, 40.979892))
            .add(LatLng(56.995663, 40.978882))
            .add(LatLng(56.999137, 40.973710))
            .add(LatLng(57.001521, 40.973780))
            .add(LatLng(57.010588, 40.972023))
            .color(Color.BLUE)

// Get back the mutable Polyline
        mMap.addPolyline(polylineOptions)
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMyLocationButtonClick(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMyLocationClick(p0: Location) {
        TODO("Not yet implemented")
    }


}