package io.travel_tunes.ui.fragments.routes.route_map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import io.travel_tunes.R
import io.travel_tunes.databinding.FragmentRouteMapBinding
import io.travel_tunes.model.route.PointItemInfo
import io.travel_tunes.utils.content.RouteSealedInfo
import io.travel_tunes.utils.extencions.changeText
import io.travel_tunes.utils.extencions.changeVisibility
import io.travel_tunes.utils.extencions.parcelable


internal class RouteMapFragment : Fragment(),
    GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var mMap: GoogleMap? = null
    private lateinit var binding: FragmentRouteMapBinding
    private lateinit var viewModelFactory: RouteMapViewModelFactory
    private lateinit var viewModel: RouteMapViewModel

    private val markers = mutableListOf<Marker>()
    private var polylineShape: Polyline? = null

    companion object {
        private const val EXTRA_ROUTE_INFO = "route_info"
        fun getInstance(routeSealedInfo: RouteSealedInfo): RouteMapFragment {
            val args = Bundle()
            val fragment = RouteMapFragment()
            args.putParcelable(EXTRA_ROUTE_INFO, routeSealedInfo)
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
        initMap()
        initViews()
        initViewModel()
    }


    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->
            mMap = googleMap
            mMap?.apply {
                setOnMarkerClickListener(this@RouteMapFragment)
                uiSettings.isZoomControlsEnabled = true
            }

            viewModel.onMapReady()
        }
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
                    Toast.makeText(
                        requireContext(),
                        "Неплохо бы сначала добавить экран, а потом уже тыкать 😉",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun initViewModel() {
        val routeSealedInfo = arguments?.parcelable<RouteSealedInfo>(EXTRA_ROUTE_INFO) ?: return
        viewModelFactory = RouteMapViewModelFactory(routeSealedInfo)

        viewModel = ViewModelProvider(this, viewModelFactory)[RouteMapViewModel::class.java]

        viewModel.routeInfo.observe(viewLifecycleOwner) { routeInfo ->
            // FIXME: наименование для toolbar задать и точки на карте отрисовать
            binding.toolbarLayout.toolbarTitle.changeText(routeInfo.getRouteItemInfo(requireContext()).getTitle())
            showRoutePoints(routeInfo)

        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val pointItemInfo = marker.tag as? PointItemInfo ?: return false
        Toast.makeText(requireContext(), pointItemInfo.getTitle(), Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationButtonClick(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMyLocationClick(p0: Location) {
        TODO("Not yet implemented")
    }

    private fun showRoutePoints(routeSealedInfo: RouteSealedInfo) {
        val routeInfo = routeSealedInfo.getRouteItemInfo(requireContext())
        mMap.let {
            val points = routeInfo.getPoints()
            points.forEach { point ->
                val iconBitmap = bitmapIconFromVector(requireContext(), R.drawable.ic_points)
                val icon = if (iconBitmap != null) {
                    BitmapDescriptorFactory.fromBitmap(iconBitmap)
                } else {
                    BitmapDescriptorFactory.defaultMarker()
                }
                mMap?.addMarker(
                    MarkerOptions()
                        .position(point.getPosition())
                        .icon(icon)
                )?.let { newMarker ->
                    newMarker.tag = point
                    markers.add(newMarker)
                }
            }

            val pointPositions = routeInfo.getRoutePolyline()
            polylineShape = mMap?.addPolylineGeofence(pointPositions)

            val bounds = LatLngBounds.builder()
                .apply {
                    pointPositions.map { position -> include(position) }
                }
                .build()
            val padding = resources.getDimensionPixelOffset(R.dimen.spacing_56)
            mMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
        }
    }


    private fun GoogleMap.addPolylineGeofence(positions: List<LatLng>): Polyline? {
        return if (positions.isNotEmpty()) {
            val mapPoints = positions.toTypedArray()
            val color = Color.BLUE
            val polylineOptions = PolylineOptions()
                .add(*mapPoints)
                .color(color)
            val polyline = addPolyline(polylineOptions)
            polyline
        } else null
    }

    private fun bitmapIconFromVector(context: Context?, vectorResId: Int): Bitmap? {
        if (context == null) return null
        ContextCompat.getDrawable(context, vectorResId)?.let {
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(it.intrinsicWidth, it.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            it.draw(canvas)
            return bitmap
        }.run {
            return null
        }
    }
}