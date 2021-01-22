package com.vision.wallpapers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vision.wallpapers.model.Wallpaper

class Adapter:RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.picture_card, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    private val differCallback: DiffUtil.ItemCallback<Wallpaper> = object : DiffUtil.ItemCallback<Wallpaper>() {
        override fun areItemsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean = oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean = oldItem.url == newItem.url

    }
    val differ = AsyncListDiffer(this, differCallback)



    override fun getItemCount(): Int = differ.currentList.size
}