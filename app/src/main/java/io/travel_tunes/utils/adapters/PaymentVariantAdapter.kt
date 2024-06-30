package io.travel_tunes.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.travel_tunes.databinding.PaymentVariantItemBinding
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.utils.extencions.changeEnabled
import io.travel_tunes.utils.extencions.changeText
import io.travel_tunes.utils.extencions.changeVisibility
import io.travel_tunes.utils.extencions.delayOnLifecycle

class PaymentVariantAdapter(private val onButtonClickListener: (PaymentVariant) -> Unit) :
    RecyclerView.Adapter<PaymentVariantAdapter.PaymentVariantItemHolder>() {

    private var itemsList: List<PaymentVariant> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentVariantItemHolder {
        val binding = PaymentVariantItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentVariantItemHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentVariantItemHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    override fun getItemCount(): Int = itemsList.size

    fun updateData(data: List<PaymentVariant>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = itemsList.size

            override fun getNewListSize(): Int = data.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                itemsList[oldItemPosition] == data[newItemPosition]

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                itemsList[oldItemPosition] == data[newItemPosition]

        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        itemsList = ArrayList(data)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class PaymentVariantItemHolder(private val binding: PaymentVariantItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(paymentVariant: PaymentVariant) {
            binding.label.changeText(
                paymentVariant.getTextForUser(binding.root.context)
            )

            with(binding.button) {
                changeText(paymentVariant.getAmountWithCurrency())
                setOnClickListener {
                    changeEnabled(false)
                    onButtonClickListener.invoke(paymentVariant)
                    delayOnLifecycle(2_000) {
                        changeEnabled(true)
                    }
                }

            }
        }
    }
}