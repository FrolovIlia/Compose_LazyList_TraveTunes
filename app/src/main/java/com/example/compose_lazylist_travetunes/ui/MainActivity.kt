package com.example.compose_lazylist_travetunes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.compose_lazylist_travetunes.R
import com.example.compose_lazylist_travetunes.databinding.ActivityInterestingPointsBinding
import com.example.compose_lazylist_travetunes.ui.fragments.cities.CitiesFragment
import com.example.compose_lazylist_travetunes.ui.fragments.points.PointsFragment

class MainActivity : AppCompatActivity(), CitiesFragment.OnFragmentInteractionListener {

    private lateinit var binding: ActivityInterestingPointsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterestingPointsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * start cities fragment
         */
        supportFragmentManager.beginTransaction()
            .add(R.id.container, CitiesFragment.getInstance())
            .commit()
    }

    override fun openPointsWithCodeName(cityCodeName: String) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, PointsFragment.getInstance(cityCodeName))
            .addToBackStack(null)
            .commit()
    }
}
