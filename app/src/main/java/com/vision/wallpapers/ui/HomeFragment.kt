package com.vision.wallpapers.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vision.wallpapers.Adapter
import com.vision.wallpapers.R
import com.vision.wallpapers.databinding.FragmentHomeBinding
import com.vision.wallpapers.repository.WallpaperRepo

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var binding: FragmentHomeBinding
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var adapter: Adapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        gridLayoutManager = object : GridLayoutManager(context, 2) {
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                lp?.height = (height / 2.5).toInt()
                return true
            }
        }

        val wallpaperRepo = WallpaperRepo()
        
        adapter = Adapter()

        setupRecyclerView(binding.homeRecyclerView, gridLayoutManager, adapter)


    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        gridLayoutManager: GridLayoutManager,
        adapter: Adapter
    ) {
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
    }
}