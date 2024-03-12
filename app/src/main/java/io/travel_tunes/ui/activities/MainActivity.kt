package io.travel_tunes.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.travel_tunes.R
import io.travel_tunes.databinding.ActivityDefaultBinding
import io.travel_tunes.ui.fragments.routes.list.RoutesFragment
import io.travel_tunes.ui.fragments.routes.route_info.RouteInfoFragment
import io.travel_tunes.ui.fragments.routes.route_map.RouteMapFragment
import io.travel_tunes.utils.content.RouteSealedInfo

class MainActivity : AppCompatActivity(), RoutesFragment.OnFragmentInteractionListener, RouteInfoFragment.OnFragmentInteractionListener {

    private lateinit var binding: ActivityDefaultBinding

    companion object {
        fun getInstance(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDefaultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * start cities fragment
         */
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, RoutesFragment.getInstance())
            .commit()
    }

    override fun openRouteInfoScreen(routeSealedInfo: RouteSealedInfo) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, RouteInfoFragment.getInstance(routeSealedInfo))
            .addToBackStack(null)
            .commit()
    }

    override fun openRouteMapScreen(routeSealedInfo: RouteSealedInfo) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, RouteMapFragment.getInstance(routeSealedInfo))
            .addToBackStack(null)
            .commit()
    }
}
