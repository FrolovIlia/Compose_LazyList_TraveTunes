package io.travel_tunes.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.travel_tunes.R
import io.travel_tunes.databinding.PhotoItemBinding

class PhotoMiniAdapter :
    RecyclerView.Adapter<PhotoMiniAdapter.PhotoItemHolder>() {

    private var itemsList: List<Int> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemHolder {
        val binding = PhotoItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoItemHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoItemHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    override fun getItemCount(): Int = itemsList.size

    fun updateData(data: List<Int>) {
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

    inner class PhotoItemHolder(private val binding: PhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(drawableRes: Int) {
            with(binding.image) {
                Glide
                    .with(this)
                    .load(drawableRes)
                    .error(R.drawable.pic_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(this)
            }
        }
    }
}