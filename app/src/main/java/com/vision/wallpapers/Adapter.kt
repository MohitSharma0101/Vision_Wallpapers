package com.vision.wallpapers

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vision.wallpapers.databinding.PictureCardBinding
import com.vision.wallpapers.model.Wallpaper

class Adapter:RecyclerView.Adapter<Adapter.ViewHolder>() {
    lateinit var binding: PictureCardBinding

    inner class ViewHolder(binding: PictureCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("URL", "acda")
        binding = PictureCardBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wallpaper = differ.currentList[position]
        holder.itemView.apply {
            loadImage(context, wallpaper.src.original, binding.wallpaperIv)

        }
    }

    private val differCallback: DiffUtil.ItemCallback<Wallpaper> = object : DiffUtil.ItemCallback<Wallpaper>() {
        override fun areItemsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean = oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean = oldItem.url == newItem.url

    }
    val differ = AsyncListDiffer(this, differCallback)


    override fun getItemCount(): Int = differ.currentList.size

    private fun loadImage(context: Context, url: String, image: ImageView) {
        Glide.with(context)
                .load(url)
                .fitCenter()
                .into(image)
    }
}