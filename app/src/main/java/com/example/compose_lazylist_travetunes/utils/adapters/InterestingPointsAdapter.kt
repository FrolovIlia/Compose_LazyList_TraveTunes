package com.example.compose_lazylist_travetunes.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.compose_lazylist_travetunes.R
import com.example.compose_lazylist_travetunes.databinding.InterestingPointItemBinding
import com.example.compose_lazylist_travetunes.model.InterestingPointEntity

class InterestingPointsAdapter(private val onButtonClickListener: (InterestingPointEntity) -> Unit) :
    RecyclerView.Adapter<InterestingPointsAdapter.InterestingPointItemHolder>() {

    private var itemsList: List<InterestingPointEntity> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestingPointItemHolder {
        val binding = InterestingPointItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return InterestingPointItemHolder(binding)
    }

    override fun onBindViewHolder(holder: InterestingPointItemHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    override fun getItemCount(): Int = itemsList.size

    fun updateData(data: List<InterestingPointEntity>) {
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

    inner class InterestingPointItemHolder(private val binding: InterestingPointItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(info: InterestingPointEntity) {
            val context = binding.root.context
            binding.cityName.text = context.resources.getText(info.title)
            binding.cityDescription.text = context.resources.getText(info.description)
            binding.moveInCity.setOnClickListener { onButtonClickListener.invoke(info) }

            with(binding.image) {
                Glide
                    .with(this)
                    .load(info.picture)
                    .error(R.drawable.ic_launcher_foreground)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(this)
            }
        }
    }
}