package com.vision.wallpapers.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.vision.wallpapers.Adapter
import com.vision.wallpapers.WallpaperViewModel
import com.vision.wallpapers.databinding.FragmentFavBinding

class FavFragment:Fragment() {
    lateinit var binding: FragmentFavBinding
    lateinit var viewModel: WallpaperViewModel
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var adapter: Adapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        gridLayoutManager = GridLayoutManager(context, 2)
        adapter = Adapter(viewModel)
        binding.favRecView.layoutManager = gridLayoutManager
        binding.favRecView.adapter = adapter

        viewModel.favWallpaper.observe(viewLifecycleOwner, {
            adapter.differ.submitList(it)
            Log.d("mohit",it.toString())
        })


    }
}