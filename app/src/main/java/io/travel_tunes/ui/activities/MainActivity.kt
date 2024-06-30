package io.travel_tunes.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.travel_tunes.R
import io.travel_tunes.databinding.ActivityDefaultBinding
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.ui.fragments.payments.PaymentsFragment
import io.travel_tunes.ui.fragments.restore.RestoreFragment
import io.travel_tunes.ui.fragments.routes.list.RoutesFragment
import io.travel_tunes.ui.fragments.routes.route_info.RouteInfoFragment
import io.travel_tunes.ui.fragments.routes.route_map.RouteMapFragment
import io.travel_tunes.utils.base.BaseActivity

class MainActivity : BaseActivity(), RoutesFragment.OnFragmentInteractionListener, RouteInfoFragment.OnFragmentInteractionListener, RouteMapFragment.OnFragmentInteractionListener {

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

    override fun openRouteInfoScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, RouteInfoFragment.getInstance())
            .addToBackStack(null)
            .commit()
    }

    override fun openRestoreScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, RestoreFragment.getInstance())
            .addToBackStack(null)
            .commit()
    }

    override fun openRouteMapScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, RouteMapFragment.getInstance())
            .addToBackStack(null)
            .commit()
    }

    override fun openPaymentsFragment(paymentVariants: List<PaymentVariant>) {
        val tag = "bottom_payments"
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            val bottomFragment = PaymentsFragment.getInstance(paymentVariants)
            bottomFragment.show(supportFragmentManager, tag)
        }
    }

    override fun openOfferAgreementsFragment() {
        startActivity(OfferAgreementsActivity.getInstance(this))
    }
}
