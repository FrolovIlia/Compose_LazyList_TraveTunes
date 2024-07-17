package io.travel_tunes.ui.fragments.routes.route_info

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.travel_tunes.R
import io.travel_tunes.appComponent
import io.travel_tunes.databinding.FragmentRouteInfoBinding
import io.travel_tunes.model.route.RouteInfoForView
import io.travel_tunes.utils.adapters.MyOuterHorizontalSpaceItemDecoration
import io.travel_tunes.utils.adapters.MySpaceItemDecoration
import io.travel_tunes.utils.adapters.PhotoMiniAdapter
import io.travel_tunes.utils.extencions.changeText
import io.travel_tunes.utils.extencions.changeVisibility
import javax.inject.Inject

class RouteInfoFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: RouteInfoViewModelFactory
    private lateinit var viewModel: RouteInfoViewModel

    private lateinit var binding: FragmentRouteInfoBinding
    private lateinit var adapterPhotos: PhotoMiniAdapter

    private var listener: OnFragmentInteractionListener? = null

    interface OnFragmentInteractionListener {
        fun openRouteMapScreen()
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

    companion object {
        fun getInstance(): RouteInfoFragment {
            val args = Bundle()
            val fragment = RouteInfoFragment()
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
                changeVisibility(false)
//                setOnClickListener {
//                    Toast.makeText(
//                        requireContext(),
//                        "Неплохо бы сначала добавить экран, а потом уже тыкать 😉",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
            }
            binding.button.setOnClickListener {
                    binding.playerView.pause()
                viewModel.routeInfoForView.value?.let {
                    listener?.openRouteMapScreen()
                }
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[RouteInfoViewModel::class.java]

        viewModel.routeInfoForView.observe(viewLifecycleOwner) { routeInfo ->
            if (routeInfo != null)
            showRouteInfo(routeInfo)
        }
    }

    private fun showRouteInfo(routeInfoForView: RouteInfoForView) {
        val routeItemInfo = routeInfoForView.getRouteItemInfo()
        with(binding) {
            toolbarLayout.toolbarTitle.apply {
                changeVisibility(true)
                changeText(routeItemInfo.getTitle())
            }
            distanceValue.changeText(routeItemInfo.getDistance())
            durationValue.changeText(routeItemInfo.getDuration())
            pointsValue.changeText(routeItemInfo.getPoints().size.toString())
            descriptionValue.changeText(routeItemInfo.getDescription())

            routeDescriptionImage.setImageResource(routeInfoForView.getRoutePictureDescriptionRes())

            val audioRes = routeInfoForView.getRouteAudioRes()
            if (audioRes != null) {
                playerView.changeVisibility(true)
                playerView.setAudioRaw(audioRes)
            } else {
                playerView.changeVisibility(false)
            }

            val photos = routeInfoForView.getRoutePointsPicturesResList()
            if (photos.isEmpty()) {
                photosRV.changeVisibility(false)
            } else {
                photosRV.changeVisibility(true)
                adapterPhotos.updateData(photos)
            }
        }
    }
}