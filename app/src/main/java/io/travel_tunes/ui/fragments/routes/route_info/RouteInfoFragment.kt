package io.travel_tunes.ui.fragments.routes.route_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.travel_tunes.R
import io.travel_tunes.data.DataGenerator
import io.travel_tunes.databinding.FragmentRouteInfoBinding
import io.travel_tunes.model.route.RouteItemInfo
import io.travel_tunes.utils.adapters.MyOuterHorizontalSpaceItemDecoration
import io.travel_tunes.utils.adapters.MyOuterVerticalSpaceItemDecoration
import io.travel_tunes.utils.adapters.MySpaceItemDecoration
import io.travel_tunes.utils.adapters.PhotoMiniAdapter
import io.travel_tunes.utils.extencions.changeText
import io.travel_tunes.utils.extencions.changeVisibility
import io.travel_tunes.utils.extencions.parcelable

class RouteInfoFragment : Fragment() {

    private lateinit var viewModelFactory: RouteInfoViewModelFactory
    private lateinit var viewModel: RouteInfoViewModel

    private lateinit var binding: FragmentRouteInfoBinding
    private lateinit var adapterPhotos: PhotoMiniAdapter

    companion object {
        private const val EXTRA_ROUTE_INFO = "route_info"
        fun getInstance(routeItemInfo: RouteItemInfo): RouteInfoFragment {
            val args = Bundle()
            val fragment = RouteInfoFragment()
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
        binding = FragmentRouteInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
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
                android.widget.Toast.makeText(
                    requireContext(),
                    "Почти даже работает, ожидайте :)",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initViewModel() {
        val routeItemInfo = arguments?.parcelable<RouteItemInfo>(EXTRA_ROUTE_INFO) ?: return
        viewModelFactory = RouteInfoViewModelFactory(routeItemInfo)

        viewModel = ViewModelProvider(this, viewModelFactory)[RouteInfoViewModel::class.java]

        viewModel.routeInfo.observe(viewLifecycleOwner) { routeInfo ->
            showRouteInfo(routeInfo)
        }
    }

    private fun showRouteInfo(routeItemInfo: RouteItemInfo) {
        with(binding) {
            toolbarLayout.toolbarTitle.apply {
                changeVisibility(true)
                changeText(routeItemInfo.getTitle())
            }
            distanceValue.changeText(routeItemInfo.getDistance())
            durationValue.changeText(routeItemInfo.getDuration())
            pointsValue.changeText(routeItemInfo.getPoints().size.toString())
            descriptionValue.changeText(routeItemInfo.getDescription())

            val photos = DataGenerator.getPointPicturesResByRouteTag(routeItemInfo.getTag())
            if (photos.isEmpty()) {
                photosRV.changeVisibility(false)
            } else {
                photosRV.changeVisibility(true)
                adapterPhotos.updateData(photos)
            }
        }
    }
}