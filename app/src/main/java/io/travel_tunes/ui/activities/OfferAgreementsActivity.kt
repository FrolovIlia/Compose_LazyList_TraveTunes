package io.travel_tunes.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.travel_tunes.R
import io.travel_tunes.databinding.ActivityDefaultBinding
import io.travel_tunes.ui.fragments.offer.OfferAgreementFragment

class OfferAgreementsActivity : AppCompatActivity() {

    companion object {
        fun getInstance(context: Context): Intent {
            return Intent(context, OfferAgreementsActivity::class.java)
        }
    }

    private lateinit var binding: ActivityDefaultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDefaultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startFirstFragment()
    }

    private fun startFirstFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, OfferAgreementFragment.getInstance())
            .commit()
    }
}