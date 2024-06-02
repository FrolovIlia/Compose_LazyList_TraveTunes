package io.travel_tunes.ui.fragments.offer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.travel_tunes.R
import io.travel_tunes.databinding.FragmentOfferBinding
import io.travel_tunes.utils.base.BaseBottomSheetDialogFragment
import io.travel_tunes.utils.extencions.changeText
import io.travel_tunes.utils.extencions.changeVisibility

class OfferAgreementFragment : BaseBottomSheetDialogFragment() {

    private lateinit var binding: FragmentOfferBinding

    companion object {
        fun getInstance(): OfferAgreementFragment {
            return OfferAgreementFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOfferBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding.toolbarLayout) {
            toolbarTitle.changeText(resources.getString(R.string.offer_screen_title))
            toolbarTitle.changeVisibility(true)
            toolbarBackArrow.apply {
                changeVisibility(true)
                setOnClickListener {
                    activity?.onBackPressed()
                }
            }
        }
    }
}