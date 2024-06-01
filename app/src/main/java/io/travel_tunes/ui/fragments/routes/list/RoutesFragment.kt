package io.travel_tunes.ui.fragments.routes.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.travel_tunes.R
import io.travel_tunes.databinding.FragmentRoutesBinding
import io.travel_tunes.utils.adapters.MyOuterVerticalSpaceItemDecoration
import io.travel_tunes.utils.adapters.MySpaceItemDecoration
import io.travel_tunes.utils.adapters.RoutesAdapter
import io.travel_tunes.utils.content.RouteSealedInfo
import io.travel_tunes.utils.extencions.changeText
import io.travel_tunes.utils.extencions.changeVisibility
import io.travel_tunes.utils.extencions.changeVisibilityInvisible
import io.travel_tunes.utils.extencions.launchWhenAtLeastLifecycleStateStarted
import io.travel_tunes.utils.prefs.PreferenceManager
import kotlinx.coroutines.flow.combine

class RoutesFragment : Fragment() {

    private lateinit var viewModelFactory: RoutesViewModelFactory
    private lateinit var viewModel: RoutesViewModel

    private lateinit var binding: FragmentRoutesBinding
    private lateinit var adapterRoutes: RoutesAdapter
    private var listener: OnFragmentInteractionListener? = null

    companion object {
        fun getInstance(): RoutesFragment {
            val args = Bundle()
            val fragment = RoutesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    interface OnFragmentInteractionListener {
        fun openRouteInfoScreen(routeSealedInfo: RouteSealedInfo)
        fun openRestoreScreen()
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoutesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        initToolbar()
        adapterRoutes = RoutesAdapter { route ->
            viewModel.handleOpenRouteEventFromAdapter(route)
        }

        binding.routesRV.adapter = adapterRoutes

        val margin16 = resources.getDimensionPixelSize(R.dimen.spacing_16)
        val margin8 = margin16 / 2
        val dividerOuter = MyOuterVerticalSpaceItemDecoration(
            topSpaceSize = margin16, bottomSpaceSize = margin16
        )
        val dividerInner = MySpaceItemDecoration(
            orientation = MySpaceItemDecoration.Orientation.VERTICAL, spaceSize = margin8
        )
        binding.routesRV.addItemDecoration(dividerOuter)
        binding.routesRV.addItemDecoration(dividerInner)
    }

    private fun initToolbar() {
        with(binding.toolbarLayout) {
            toolbarBackArrow.changeVisibilityInvisible()
            toolbarSettings.apply {
                changeVisibility(true)
                setOnClickListener {
//                    Toast.makeText(
//                        requireContext(),
//                        "Неплохо бы сначала добавить экран, а потом уже тыкать 😉",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    listener?.openRestoreScreen()
                }
            }
            toolbarTitle.apply {
                changeVisibility(true)
                changeText(resources.getString(R.string.fragment_routes_title))
            }
        }
    }

    private fun initViewModel() {
        val preferenceManager = PreferenceManager(requireContext())
        viewModelFactory = RoutesViewModelFactory(preferenceManager)
        viewModel = ViewModelProvider(this, viewModelFactory)[RoutesViewModel::class.java]

        launchWhenAtLeastLifecycleStateStarted {
            viewModel.routesNew.combine(viewModel.routePaidTagsSet) { routesList, routesPaidTagsSet ->
                routesList.map { routeSealedInfo ->
                    routeSealedInfo.setIsPaid(routesPaidTagsSet.contains(routeSealedInfo.getRouteTag()))
                    routeSealedInfo
                }
            }.collect { resultList ->
                adapterRoutes.updateData(resultList)
            }
        }

        viewModel.openRouteInfoScreenEvent.observe(viewLifecycleOwner) { route ->
            if (route == null) return@observe
            viewModel.clearOpenRouteInfoScreenEvent()
            listener?.openRouteInfoScreen(route)
        }
    }
}