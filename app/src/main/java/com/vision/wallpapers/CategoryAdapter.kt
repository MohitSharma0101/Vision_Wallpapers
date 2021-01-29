package com.vision.wallpapers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.vision.wallpapers.databinding.CategoryCardBinding
import com.vision.wallpapers.model.alphaCoder.AlphaCategoryResponseItem

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(var binding: CategoryCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = differ.currentList[position]
        holder.apply {
            binding.categoryIv.load(category.url)
        }
    }

    private val differCallback: DiffUtil.ItemCallback<AlphaCategoryResponseItem> =
        object : DiffUtil.ItemCallback<AlphaCategoryResponseItem>() {
            override fun areItemsTheSame(
                oldItem: AlphaCategoryResponseItem,
                newItem: AlphaCategoryResponseItem
            ): Boolean {
                return newItem.name == oldItem.name
            }

            override fun areContentsTheSame(
                oldItem: AlphaCategoryResponseItem,
                newItem: AlphaCategoryResponseItem
            ): Boolean {
                return newItem.name == oldItem.name
            }

        }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int = differ.currentList.size

}