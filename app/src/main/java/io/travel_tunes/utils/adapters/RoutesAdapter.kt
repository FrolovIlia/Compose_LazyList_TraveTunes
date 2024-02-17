package io.travel_tunes.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.travel_tunes.R
import io.travel_tunes.databinding.RouteItemBinding
import io.travel_tunes.model.route.RouteItemInfo
import io.travel_tunes.utils.extencions.changeText

class RoutesAdapter(private val onButtonClickListener: (RouteItemInfo) -> Unit) :
    RecyclerView.Adapter<RoutesAdapter.RouteItemHolder>() {

    private var itemsList: List<RouteItemInfo> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteItemHolder {
        val binding = RouteItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RouteItemHolder(binding)
    }

    override fun onBindViewHolder(holder: RouteItemHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    override fun getItemCount(): Int = itemsList.size

    fun updateData(data: List<RouteItemInfo>) {
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

    inner class RouteItemHolder(private val binding: RouteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(route: RouteItemInfo) {
            val context = binding.root.context
            binding.routeTitle.changeText(route.getTitle())
            binding.routeDescription.changeText(route.getDescription())
            binding.showRouteInfo.setOnClickListener { onButtonClickListener.invoke(route) }

            val pictureRes = when(route.getId()) {
                "1" -> R.drawable.pic_ivanovo_city
                else -> R.drawable.pic_default
            }

            with(binding.image) {
                Glide
                    .with(this)
                    .load(pictureRes)
                    .error(R.drawable.ic_launcher_foreground)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(this)
            }
        }
    }
}