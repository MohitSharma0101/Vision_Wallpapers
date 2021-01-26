package com.vision.wallpapers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vision.wallpapers.databinding.PictureCardBinding
import com.vision.wallpapers.model.unsplash.Result

class Adapter:RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(var binding: PictureCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PictureCardBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = differ.currentList.get(position)
        holder.apply {
            loadImage(itemView.context, photo.urls.full, binding.wallpaperIv)
        }
    }

    private val differCallback: DiffUtil.ItemCallback<Result> = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.urls.full == newItem.urls.full
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.urls.full == newItem.urls.full
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int = differ.currentList.size

    private fun loadImage(context: Context, url: String, image: ImageView) {
        Glide.with(context)
                .load(url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(image)
    }
}