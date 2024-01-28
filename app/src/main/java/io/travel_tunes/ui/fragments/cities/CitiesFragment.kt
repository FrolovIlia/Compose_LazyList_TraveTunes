package io.travel_tunes.ui.fragments.cities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.travel_tunes.databinding.FragmentCitiesBinding
import io.travel_tunes.utils.adapters.CitiesAdapter

class CitiesFragment: Fragment() {

    private lateinit var viewModelFactory: CitiesViewModelFactory
    private lateinit var viewModel: CitiesViewModel

    private lateinit var binding: FragmentCitiesBinding
    private lateinit var adapterCities: CitiesAdapter

    private var listener: OnFragmentInteractionListener? = null

    companion object {
        fun getInstance() = CitiesFragment()
    }

    interface OnFragmentInteractionListener {
        fun openPointsWithCodeName(cityCodeName: String)
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        adapterCities = CitiesAdapter { city ->
            /**
             * вот тут мы понимаем на какой элемент произошло нажатие в RecyclerView
             */
            listener?.openPointsWithCodeName(cityCodeName = city.codeName)
            Toast.makeText(requireContext(), "${this.resources.getText(city.name)}", Toast.LENGTH_SHORT).show()
        }

        binding.cityRecyclerView.adapter = adapterCities
    }

    private fun initViewModel() {
        val repository = (activity?.application as io.travel_tunes.InterestingPointsApp).repository
        viewModelFactory = CitiesViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory)[CitiesViewModel::class.java]

        viewModel.cities.observe(viewLifecycleOwner) { points ->
            adapterCities.updateData(points)
        }
    }
}