package com.vision.wallpapers

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vision.wallpapers.databinding.PictureCardBinding
import com.vision.wallpapers.model.Response
import com.vision.wallpapers.util.Palette
import java.util.*

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
        val photo = differ.currentList[position]
        holder.apply {
            binding.wallpaperIv.load(photo.urlImage){
                crossfade(true)
                allowHardware(true)
                placeholder(Color.parseColor( Palette.LIGHT.random()).toDrawable())
            }
        }
    }

    private val differCallback: DiffUtil.ItemCallback<Response> = object : DiffUtil.ItemCallback<Response>() {
        override fun areItemsTheSame(oldItem: Response, newItem: Response): Boolean {
            return oldItem.idR == newItem.idR
        }

        override fun areContentsTheSame(oldItem: Response, newItem: Response): Boolean {
            return oldItem.idR == newItem.idR
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int = differ.currentList.size

    private fun loadImage(context: Context, url: String, image: ImageView, thumb: String) {
        Glide.with(context)
                .load(thumb)
                .thumbnail(0.01f)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image)
    }
    private fun removeBackSlash(s: String):String{
        return s.replace("\\/", "\\")
    }
}