package io.travel_tunes.ui.fragments.routes.route_info

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import io.travel_tunes.R
import io.travel_tunes.databinding.FragmentRouteInfoBinding
import io.travel_tunes.utils.Constants
import io.travel_tunes.utils.adapters.MyOuterHorizontalSpaceItemDecoration
import io.travel_tunes.utils.adapters.MySpaceItemDecoration
import io.travel_tunes.utils.adapters.PhotoMiniAdapter
import io.travel_tunes.utils.content.RouteSealedInfo
import io.travel_tunes.utils.extencions.changeText
import io.travel_tunes.utils.extencions.changeVisibility
import io.travel_tunes.utils.extencions.parcelable
import io.travel_tunes.utils.map.SomeMapInterface

class RouteInfoFragment : Fragment() {

    private lateinit var viewModelFactory: RouteInfoViewModelFactory
    private lateinit var viewModel: RouteInfoViewModel

    private lateinit var binding: FragmentRouteInfoBinding
    private lateinit var adapterPhotos: PhotoMiniAdapter

    private var listener: OnFragmentInteractionListener? = null

    private var someMap: SomeMapInterface? = null

    interface OnFragmentInteractionListener {
        fun openRouteMapScreen(routeSealedInfo: RouteSealedInfo)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        private const val EXTRA_ROUTE_INFO = "route_info"
        private const val MAP_VIEW_BUNDLE_KEY = "map_view_bundle_key"
        fun getInstance(routeSealedInfo: RouteSealedInfo): RouteInfoFragment {
            val args = Bundle()
            val fragment = RouteInfoFragment()
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
        binding = FragmentRouteInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initMap(savedInstanceState)
        initViewModel()
    }

    override fun onStart() {
        binding.mapView.onStart()
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
        view?.requestApplyInsets()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onStop() {
        binding.mapView.onStop()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveMapInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    private fun saveMapInstanceState(outState: Bundle?) {
        val mapViewBundle = Bundle()
        binding.mapView.onSaveInstanceState(mapViewBundle)
        outState?.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
    }

    private fun initViews() {
        initToolbar()
        adapterPhotos = PhotoMiniAdapter()

        val margin16 = resources.getDimensionPixelSize(R.dimen.spacing_16)
        val margin8 = margin16 / 2
        val dividerOuter = MyOuterHorizontalSpaceItemDecoration(
            startSpaceSize = margin16,
            endSpaceSize = margin16
        )
        val dividerInner = MySpaceItemDecoration(
            orientation = MySpaceItemDecoration.Orientation.HORIZONTAL,
            spaceSize = margin8
        )
        binding.photosRV.addItemDecoration(dividerOuter)
        binding.photosRV.addItemDecoration(dividerInner)

        with(binding.photosRV) {
            adapter = adapterPhotos
            setHasFixedSize(true)
            addItemDecoration(dividerInner)
        }
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
            binding.button.setOnClickListener {
                viewModel.routeInfo.value?.let {
                    listener?.openRouteMapScreen(routeSealedInfo = it)
                }
            }
        }
    }

    private fun initViewModel() {
        val routeSealedInfo = arguments?.parcelable<RouteSealedInfo>(EXTRA_ROUTE_INFO) ?: return
        viewModelFactory = RouteInfoViewModelFactory(routeSealedInfo)

        viewModel = ViewModelProvider(this, viewModelFactory)[RouteInfoViewModel::class.java]

        viewModel.routeInfo.observe(viewLifecycleOwner) { routeInfo ->
            showRouteInfo(routeInfo)
        }
    }

    private fun initMap(savedInstanceState: Bundle?) {
        val mapViewBundle = savedInstanceState?.getBundle(MAP_VIEW_BUNDLE_KEY)
        with(binding.mapView) {
            onCreate(mapViewBundle)
            getMapAsync {
                onMapReady(it)
            }
        }
    }

    private fun onMapReady(map: SomeMapInterface) {
        someMap = map
        map.setUiSettings(
            context = requireContext(),
            isMapToolbarEnabled = false,
            isZoomControlsEnabled = true,
            isRotateGesturesEnabled = false,
            isCompassEnabled = false,
            isMyLocationButtonEnabled = false
        )

        viewModel.routeInfo.value?.getRouteItemInfo(requireContext())?.let { routeItemInfo ->
            map.addRouteMarkers(requireContext(), routeItemInfo, mapPadding = resources.getDimensionPixelOffset(R.dimen.spacing_56))
        }

    }

    private fun showRouteInfo(routeSealedInfo: RouteSealedInfo) {
        val routeItemInfo = routeSealedInfo.getRouteItemInfo(requireContext())
        with(binding) {
            toolbarLayout.toolbarTitle.apply {
                changeVisibility(true)
                changeText(routeItemInfo.getTitle())
            }
            distanceValue.changeText(routeItemInfo.getDistance())
            durationValue.changeText(routeItemInfo.getDuration())
            pointsValue.changeText(routeItemInfo.getPoints().size.toString())
            descriptionValue.changeText(routeItemInfo.getDescription())

            val audioRes = routeSealedInfo.getRouteAudioRes()
            if (audioRes != null) {
                playerView.changeVisibility(true)
                playerView.setAudioRaw(audioRes)
            } else {
                playerView.changeVisibility(false)
            }

            val photos = routeSealedInfo.getPointPictureResList()
            if (photos.isEmpty()) {
                photosRV.changeVisibility(false)
            } else {
                photosRV.changeVisibility(true)
                adapterPhotos.updateData(photos)
            }
        }
    }
}