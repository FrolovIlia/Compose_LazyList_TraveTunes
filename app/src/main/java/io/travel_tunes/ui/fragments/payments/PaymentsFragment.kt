package io.travel_tunes.ui.fragments.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import io.travel_tunes.databinding.FragmentPaymentsBinding
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.model.payments.PaymentVariantsParcelable
import io.travel_tunes.utils.base.BaseBottomSheetDialogFragment
import io.travel_tunes.utils.extencions.changeText
import io.travel_tunes.utils.extencions.changeVisibility
import io.travel_tunes.utils.extencions.parcelable
import io.travel_tunes.utils.prefs.PreferenceManager

class PaymentsFragment : BaseBottomSheetDialogFragment() {

    private lateinit var viewModelFactory: PaymentsViewModelFactory
    private lateinit var viewModel: PaymentsViewModel

    private lateinit var binding: FragmentPaymentsBinding

    companion object {
        private const val EXTRA_PAYMENT_VARIANTS = "payment_variants"
        fun getInstance(paymentVariants: List<PaymentVariant>): PaymentsFragment {
            val args = Bundle()
            val fragment = PaymentsFragment()
            args.putParcelable(EXTRA_PAYMENT_VARIANTS, PaymentVariantsParcelable(paymentVariants))
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        binding.dialogClose.setOnClickListener { dismiss() }
        binding.dialogInfo.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Показать всплывашку оферты",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initViewModel() {
        val paymentVariants = arguments?.parcelable<PaymentVariantsParcelable>(
            EXTRA_PAYMENT_VARIANTS
        )?.getPaymentVariants() ?: return
        val preferenceManager = PreferenceManager(requireContext())
        viewModelFactory = PaymentsViewModelFactory(paymentVariants, preferenceManager)
        viewModel = ViewModelProvider(this, viewModelFactory)[PaymentsViewModel::class.java]

        viewModel.paymentVariantsLiveData.observe(viewLifecycleOwner) { paymentVariants ->
            paymentVariants.first().let { paymentVariant ->
                binding.buyCurrentLabel.changeText(
                    paymentVariant.getTextForUser(binding.root.context)
                )
                binding.buyCurrentBtn.changeText(paymentVariant.getAmount())
                binding.buyCurrentBtn.setOnClickListener {
                    Toast.makeText(
                        requireContext(),
                        "${paymentVariant.getName()} покупаем",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            paymentVariants.getOrNull(1).let { paymentVariant ->
                if (paymentVariant == null) {
                    binding.buyBothLabel.changeVisibility(false)
                    binding.buyBothBtn.changeVisibility(false)
                } else {
                    binding.buyBothLabel.changeVisibility(true)
                    binding.buyBothBtn.changeVisibility(true)
                    binding.buyCurrentLabel.changeText(
                        paymentVariant.getTextForUser(binding.root.context)
                    )
                    binding.buyCurrentBtn.changeText(paymentVariant.getAmount())
                }
            }
        }
    }
}