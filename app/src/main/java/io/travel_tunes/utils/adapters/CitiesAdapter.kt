package io.travel_tunes.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.travel_tunes.R
import io.travel_tunes.databinding.CityItemBinding
import io.travel_tunes.model.CityEntity

class CitiesAdapter(private val onButtonClickListener: (CityEntity) -> Unit) :
    RecyclerView.Adapter<CitiesAdapter.CityItemHolder>() {

    private var itemsList: List<CityEntity> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItemHolder {
        val binding = CityItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CityItemHolder(binding)
    }

    override fun onBindViewHolder(holder: CityItemHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    override fun getItemCount(): Int = itemsList.size

    fun updateData(data: List<CityEntity>) {
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

    inner class CityItemHolder(private val binding: CityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(info: CityEntity) {
            val context = binding.root.context
            binding.cityName.text = context.resources.getText(info.name)
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