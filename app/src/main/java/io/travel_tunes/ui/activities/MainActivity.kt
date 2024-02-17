package io.travel_tunes.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.travel_tunes.R
import io.travel_tunes.databinding.ActivityInterestingPointsBinding
import io.travel_tunes.ui.fragments.cities.CitiesFragment
import io.travel_tunes.ui.fragments.points.PointsFragment

class MainActivity : AppCompatActivity(), CitiesFragment.OnFragmentInteractionListener {

    private lateinit var binding: ActivityInterestingPointsBinding

    companion object {
        fun getInstance(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterestingPointsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * start cities fragment
         */
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CitiesFragment.getInstance())
            .commit()
    }

    override fun openPointsWithCodeName(cityCodeName: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PointsFragment.getInstance(cityCodeName))
            .addToBackStack(null)
            .commit()
    }
}
