package io.travel_tunes.ui.fragments.routes.route_map

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import io.travel_tunes.R
import io.travel_tunes.appComponent
import io.travel_tunes.databinding.FragmentRouteMapBinding
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.model.route.PointItemFullInfo
import io.travel_tunes.ui.fragments.points.info.PointInfoFragment
import io.travel_tunes.utils.FragmentResultUtils
import io.travel_tunes.utils.base.BaseActivity
import io.travel_tunes.utils.extencions.changeText
import io.travel_tunes.utils.extencions.changeVisibility
import io.travel_tunes.utils.extencions.toDp
import io.travel_tunes.utils.map.GoogleMapManager
import io.travel_tunes.utils.permissions.MapLocationManager
import io.travel_tunes.utils.permissions.PermissionLocationHelper
import javax.inject.Inject

class RouteMapFragment : Fragment() {

    private lateinit var binding: FragmentRouteMapBinding
    private lateinit var viewModel: RouteMapViewModel

    @Inject
    lateinit var viewModelFactory: RouteMapViewModelFactory

    private var googleMapManager: GoogleMapManager? = null
    private var mapLocationManager: MapLocationManager? = null
    private var permissionHelper: PermissionLocationHelper? = null

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    private var listener: OnFragmentInteractionListener? = null

    companion object {
        /**
         * Request code for location permission request.
         *
         * @see .onRequestPermissionsResult
         */
        private const val REQUESTING_LOCATION_UPDATES_KEY = "location_updates_key"
        fun getInstance(): RouteMapFragment {
            val args = Bundle()
            val fragment = RouteMapFragment()
            fragment.arguments = args
            return fragment
        }
    }

    interface OnFragmentInteractionListener {
        fun openPaymentsFragment(paymentVariants: List<PaymentVariant>)
        fun openOfferAgreementsFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            mapLocationManager?.setRequestingLocationUpdates(
                isRequesting = savedInstanceState.getBoolean(REQUESTING_LOCATION_UPDATES_KEY, false)
            )
        }
        initMap()
        initViews()
        initBottomSheet()
        initViewModel()
        initFragmentResultListeners()
        initLocationManagers()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(
            REQUESTING_LOCATION_UPDATES_KEY,
            mapLocationManager?.isRequestingLocationUpdates() ?: false
        )
        super.onSaveInstanceState(outState)
    }


    private fun initFragmentResultListeners() {
        setFragmentResultListener(FragmentResultUtils.REQUEST_OPEN_OFFER_AGREEMENTS) { _, bundle ->
            val isOpenNeed = bundle.getBoolean(FragmentResultUtils.BUNDLE_OPEN_OFFER_AGREEMENTS)
            if (isOpenNeed) {
                listener?.openOfferAgreementsFragment()
            }
        }
        setFragmentResultListener(FragmentResultUtils.REQUEST_UPDATE_ROUTE_PAID_AFTER_BUY) { _, bundle ->
            val isUpdateNeed =
                bundle.getBoolean(FragmentResultUtils.BUNDLE_UPDATE_ROUTE_PAID_AFTER_BUY)
            if (isUpdateNeed) {
                viewModel.updateRouteInfoAfterSuccessBuy()
            }
        }
    }

    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapContainer) as? SupportMapFragment
        mapFragment?.getMapAsync { onMapReady(it) }
    }

    private fun initViews() {
        initToolbar()
        binding.btnLocationLayout.setOnClickListener { getMyLocation() }
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
                changeVisibility(false)
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[RouteMapViewModel::class.java]

        viewModel.routeTitle.observe(viewLifecycleOwner) { routeTitle ->
            binding.toolbarLayout.toolbarTitle.changeText(routeTitle)
        }
        viewModel.routeItemInfo.observe(viewLifecycleOwner) { routeItemInfo ->
            googleMapManager?.updateRouteMarkers(
                routeItemInfo,
                mapPadding = resources.getDimensionPixelOffset(R.dimen.spacing_56)
            )
        }
        viewModel.routeMapPoints.observe(viewLifecycleOwner) {
            if (it != null) {
                googleMapManager?.updatePolygon(
                    routeMapPoints = it
                )
            }
        }
        viewModel.openPointInfoScreen.observe(viewLifecycleOwner) { pointInfo ->
            if (pointInfo == null) return@observe
            openPointInfoBottomFragment(
                pointItemFullInfo = pointInfo
            )
            viewModel.clearOpenPointInfoScreen()
        }
        viewModel.selectedRouteSealedInfo.observe(viewLifecycleOwner) {}
        viewModel.openPaymentsScreen.observe(viewLifecycleOwner) { paymentVariants ->
            if (paymentVariants == null) return@observe
            listener?.openPaymentsFragment(paymentVariants)
            viewModel.clearOpenPaymentsScreen()
        }

        viewModel.hidePointInfoBottomFragmentEvent.observe(viewLifecycleOwner) { isHideNeed ->
            if (isHideNeed == true) {
                viewModel.clearHidePointInfoBottomFragmentEvent()
                hidePointInfoBottomFragment()
            }
        }
    }

    private fun initBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomContainer)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    viewModel.bottomSheetIsHidden()
                }
            }
        })
    }

    private fun onMapReady(map: GoogleMap) {
        googleMapManager = GoogleMapManager(map)
        googleMapManager?.setUiSettings(
            context = requireContext(),
            isMapToolbarEnabled = false,
            isZoomControlsEnabled = true,
            isRotateGesturesEnabled = false,
            isCompassEnabled = false,
            isMyLocationButtonEnabled = true
        )

        googleMapManager?.setClusterManagers(
            context = requireContext(),
            pointItemClickCallback = { pointItemInfo ->
                viewModel.handleOnMarkerPointClick(pointItemInfo)
            }
        )

        googleMapManager?.setOnMapClickListener {
            viewModel.handleOnMapClick()
        }
        viewModel.onMapReady()
    }

    private fun openPointInfoBottomFragment(pointItemFullInfo: PointItemFullInfo) {
        bottomSheetBehavior.apply {
            peekHeight = 240.toDp(requireContext())
            state = BottomSheetBehavior.STATE_COLLAPSED
            skipCollapsed = false
            isHideable = true
        }
        var fragment =
            childFragmentManager.findFragmentByTag(PointInfoFragment.POINT_INFO_BOTTOM) as? PointInfoFragment
        if (fragment == null) {
            fragment = PointInfoFragment.getInstance(pointItemFullInfo)
            childFragmentManager
                .beginTransaction()
                .replace(
                    R.id.bottomContainer,
                    fragment,
                    PointInfoFragment.POINT_INFO_BOTTOM
                )
                .setCustomAnimations(
                    R.anim.fragment_slide_top_enter,
                    R.anim.fragment_slide_top_exit,
                    R.anim.fragment_slide_bottom_enter,
                    R.anim.fragment_slide_bottom_exit
                )
                .commit()

        } else {
            fragment.updateData(pointItemFullInfo)
        }
        if (fragment.isHidden) {
            childFragmentManager.beginTransaction().show(fragment).commit()
        }
    }

    private fun hidePointInfoBottomFragment() {
        val fragment =
            childFragmentManager.findFragmentByTag(PointInfoFragment.POINT_INFO_BOTTOM) as? PointInfoFragment
        if (fragment != null) {
            fragment.stopPlayer()
            childFragmentManager.beginTransaction().hide(fragment).commit()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMyLocation() {
        permissionHelper?.launchIfLocationPermissionGrantedAndLocationEnabled(
            action = {
                mapLocationManager?.getMyLocation(
                    activity = activity
                )
            }
        )
    }

    private fun initLocationManagers() {
        mapLocationManager = MapLocationManager(
            activity = requireActivity(),
            anotherLocationResult = {},
            firstLocationResult = {
                it?.let { location ->
                    googleMapManager?.initMyLocation(
                        location.latitude, location.longitude, isNeedCenterMap = true
                    )
                }
            }
        )
        mapLocationManager?.initLocation()

        permissionHelper = PermissionLocationHelper(
            activity = activity as BaseActivity,
            fragment = this,
            onPermissionGranted = {
                googleMapManager?.setIsMyLocationEnabled(
                    true,
                    requireActivity()
                )
            },
            onPermissionNonGranted = {
                googleMapManager?.setIsMyLocationEnabled(
                    false,
                    requireActivity()
                )
            }
        )
    }
}