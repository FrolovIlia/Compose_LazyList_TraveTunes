package com.example.compose_lazylist_travetunes.ui.fragments.points

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.compose_lazylist_travetunes.InterestingPointsApp
import com.example.compose_lazylist_travetunes.databinding.FragmentPointsBinding
import com.example.compose_lazylist_travetunes.utils.adapters.InterestingPointsAdapter

class PointsFragment : Fragment() {

    private lateinit var viewModelFactory: PointsViewModelFactory
    private lateinit var viewModel: PointsViewModel

    private lateinit var binding: FragmentPointsBinding
    private lateinit var adapterPoints: InterestingPointsAdapter

    companion object {
        private const val EXTRA_CITY_CODE_NAME = "city_code_name"
        fun getInstance(cityCodeName: String): PointsFragment {
            val args = Bundle()
            val fragment = PointsFragment()
            args.putString(EXTRA_CITY_CODE_NAME, cityCodeName)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPointsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        adapterPoints = InterestingPointsAdapter { point ->
            /**
             * вот тут мы понимаем на какой элемент произошло нажатие в RecyclerView
             */
            Toast.makeText(
                requireContext(),
                "${this.resources.getText(point.title)}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.pointRecyclerView.adapter = adapterPoints
    }

    private fun initViewModel() {
        val cityCodeName = arguments?.getString(EXTRA_CITY_CODE_NAME) ?: return
        val repository = (activity?.application as InterestingPointsApp).repository
        viewModelFactory = PointsViewModelFactory(repository, cityCodeName)

        viewModel = ViewModelProvider(this, viewModelFactory)[PointsViewModel::class.java]

        viewModel.points.observe(viewLifecycleOwner) { points ->
            adapterPoints.updateData(points)
        }
    }
}