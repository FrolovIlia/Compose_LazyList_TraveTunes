package io.travel_tunes.ui.fragments.routes.route_map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.travel_tunes.databinding.FragmentRouteMapBinding
import io.travel_tunes.utils.content.RouteSealedInfo
import io.travel_tunes.utils.extencions.changeVisibility
import io.travel_tunes.utils.extencions.parcelable

class RouteMapFragment : Fragment() {

    private lateinit var viewModelFactory: RouteMapViewModelFactory
    private lateinit var viewModel: RouteMapViewModel

    private lateinit var binding: FragmentRouteMapBinding

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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
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
        val routeSealedInfo = arguments?.parcelable<RouteSealedInfo>(EXTRA_ROUTE_INFO) ?: return
        viewModelFactory = RouteMapViewModelFactory(routeSealedInfo)

        viewModel = ViewModelProvider(this, viewModelFactory)[RouteMapViewModel::class.java]

        viewModel.routeInfo.observe(viewLifecycleOwner) { routeInfo ->
            // FIXME: наименование для toolbar задать и точки на карте отрисовать
//            showRoutePoints(routeInfo)
            // тут может быть реализация отображения маркеров на карте (points)
        }
    }
}