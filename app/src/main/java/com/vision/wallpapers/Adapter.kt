package com.vision.wallpapers

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vision.wallpapers.databinding.PictureCardBinding
import com.vision.wallpapers.model.Response
import com.vision.wallpapers.model.alphaCoder.AlphaPhotoResponseItem
import com.vision.wallpapers.util.Palette
import kotlinx.coroutines.launch

class Adapter(private val viewModel: WallpaperViewModel):RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(var binding: PictureCardBinding) : RecyclerView.ViewHolder(binding.root)
    private var onItemClickListener:( (View,String) -> Unit )? = null
    private var onSaveClickListener:( (AlphaPhotoResponseItem) -> Unit )? = null

    fun setOnItemClickListener(listener: (View,String) -> Unit){
        onItemClickListener = listener
    }
    fun setSaveOnClickListener(listener: (AlphaPhotoResponseItem) -> Unit){
        onSaveClickListener = listener
    }

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
        var isSaved = false
        holder.apply {
            binding.wallpaperIv.load(photo.urlImage){
                crossfade(true)
                allowHardware(true)
                placeholder(Color.parseColor( Palette.LIGHT[position % Palette.LIGHT.size] ).toDrawable())
            }
            viewModel.viewModelScope.launch {
                if(viewModel.isWallpaperSaved(photo as AlphaPhotoResponseItem)){
                    binding.favBtn.setImageResource(R.drawable.ic_heart_filled)
                    isSaved = true
                }else{
                    binding.favBtn.setImageResource(R.drawable.heart_white)
                    isSaved = false
                }
            }

            binding.favBtn.setOnClickListener {
                if(!isSaved){
                    binding.favBtn.setImageResource(R.drawable.ic_heart_filled)
                    viewModel.saveWallpaper(photo as AlphaPhotoResponseItem)
                }else{
                    binding.favBtn.setImageResource(R.drawable.heart_white)
                    viewModel.deleteWallpaper(photo as AlphaPhotoResponseItem)
                }
                //  notifyDataSetChanged()
            }
            binding.wallpaperIv.setOnClickListener {
                onItemClickListener?.let {
                    it(binding.wallpaperIv,photo.urlImage)
                }
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
}